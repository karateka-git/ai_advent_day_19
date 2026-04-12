Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"
[Console]::InputEncoding = [System.Text.Encoding]::UTF8
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$OutputEncoding = [System.Text.Encoding]::UTF8

function Stop-ListeningProcess {
    param(
        [Parameter(Mandatory = $true)]
        [int]$Port
    )

    Get-NetTCPConnection -LocalPort $Port -State Listen -ErrorAction SilentlyContinue |
        ForEach-Object {
            Stop-Process -Id $_.OwningProcess -Force -ErrorAction SilentlyContinue
        }
}

function Wait-Port {
    param(
        [Parameter(Mandatory = $true)]
        [string]$TargetHost,

        [Parameter(Mandatory = $true)]
        [int]$Port,

        [int]$Attempts = 30,

        [int]$DelaySeconds = 1
    )

    for ($i = 0; $i -lt $Attempts; $i++) {
        try {
            $tcpClient = New-Object Net.Sockets.TcpClient
            $tcpClient.Connect($TargetHost, $Port)
            $tcpClient.Close()
            return
        } catch {
            Start-Sleep -Seconds $DelaySeconds
        }
    }

    throw "Server did not start listening on $TargetHost`:$Port in time."
}

function Get-ClientBatPath {
    param(
        [Parameter(Mandatory = $true)]
        [string]$ProjectRoot
    )

    return Join-Path $ProjectRoot "build\install\mcp-client\bin\mcp-client.bat"
}

function Get-ServerBatPath {
    param(
        [Parameter(Mandatory = $true)]
        [string]$ProjectRoot
    )

    return Join-Path $ProjectRoot "build\install\mcp-server\bin\mcp-server.bat"
}

function Assert-LauncherExists {
    param(
        [Parameter(Mandatory = $true)]
        [string]$Path,

        [Parameter(Mandatory = $true)]
        [string]$Description
    )

    if (-not (Test-Path -LiteralPath $Path)) {
        throw "$Description not found: $Path. Run .\gradlew.bat build, .\gradlew.bat installClientDist and .\gradlew.bat installServerDist first."
    }
}

function Ensure-Launchers {
    param(
        [Parameter(Mandatory = $true)]
        [string]$ProjectRoot,

        [Parameter(Mandatory = $true)]
        [string]$ClientBatPath,

        [Parameter(Mandatory = $true)]
        [string]$ServerBatPath
    )

    if ((Test-Path -LiteralPath $ClientBatPath) -and (Test-Path -LiteralPath $ServerBatPath)) {
        return
    }

    Write-Output "Direct launchers not found. Building install distributions..."
    & .\gradlew.bat installClientDist installServerDist
    if ($LASTEXITCODE -ne 0) {
        throw "Failed to build direct launchers. Gradle exit code: $LASTEXITCODE."
    }
}

function Assert-OutputContains {
    param(
        [Parameter(Mandatory = $true)]
        [string]$Text,

        [Parameter(Mandatory = $true)]
        [string[]]$ExpectedFragments
    )

    foreach ($fragment in $ExpectedFragments) {
        if (-not $Text.Contains($fragment)) {
            throw "Expected fragment was not found in client output: $fragment"
        }
    }
}

function Start-HeadlessLauncherProcess {
    param(
        [Parameter(Mandatory = $true)]
        [string]$ProjectRoot,

        [Parameter(Mandatory = $true)]
        [string]$BatPath,

        [Parameter(Mandatory = $true)]
        [string]$StdoutPath,

        [Parameter(Mandatory = $true)]
        [string]$StderrPath
    )

    $command = "chcp 65001>nul && `"$BatPath`""

    return Start-Process `
        -FilePath "cmd.exe" `
        -ArgumentList "/c", $command `
        -WorkingDirectory $ProjectRoot `
        -RedirectStandardOutput $StdoutPath `
        -RedirectStandardError $StderrPath `
        -PassThru
}

function Stop-HeadlessLauncherProcess {
    param(
        [Parameter(Mandatory = $true)]
        [System.Diagnostics.Process]$Process
    )

    if ($Process) {
        try {
            if (-not $Process.HasExited) {
                Stop-Process -Id $Process.Id -Force -ErrorAction SilentlyContinue
            }
        } finally {
            $Process.Dispose()
        }
    }
}

function Invoke-Utf8ClientCommands {
    param(
        [Parameter(Mandatory = $true)]
        [string]$ProjectRoot,

        [Parameter(Mandatory = $true)]
        [string]$StdoutPath,

        [Parameter(Mandatory = $true)]
        [string]$StderrPath
    )

    $null = & powershell -ExecutionPolicy Bypass -File (Join-Path $ProjectRoot "scripts\invoke-client-commands.ps1") `
        -ProjectRoot $ProjectRoot `
        -Commands @("connect", "exit") `
        -StdoutPath $StdoutPath `
        -StderrPath $StderrPath

    return $LASTEXITCODE
}

$projectRoot = Split-Path -Parent $PSScriptRoot
Set-Location $projectRoot

$serverHost = "127.0.0.1"
$serverPort = 3000
$serverEndpoint = "http://$serverHost`:$serverPort/mcp"

$tmpDir = Join-Path $projectRoot "build\tmp\e2e"
New-Item -ItemType Directory -Force -Path $tmpDir | Out-Null

$serverStdout = Join-Path $tmpDir "server.out"
$serverStderr = Join-Path $tmpDir "server.err"
$clientStdout = Join-Path $tmpDir "client.out"
$clientStderr = Join-Path $tmpDir "client.err"

$clientBatPath = Get-ClientBatPath -ProjectRoot $projectRoot
$serverBatPath = Get-ServerBatPath -ProjectRoot $projectRoot
Ensure-Launchers -ProjectRoot $projectRoot -ClientBatPath $clientBatPath -ServerBatPath $serverBatPath
Assert-LauncherExists -Path $clientBatPath -Description "Built client launcher"
Assert-LauncherExists -Path $serverBatPath -Description "Built server launcher"

@($serverStdout, $serverStderr, $clientStdout, $clientStderr) | ForEach-Object {
    if (Test-Path $_) {
        Remove-Item $_ -Force
    }
}

$serverProcess = $null

try {
    Stop-ListeningProcess -Port $serverPort

    $serverProcess = Start-HeadlessLauncherProcess `
        -ProjectRoot $projectRoot `
        -BatPath $serverBatPath `
        -StdoutPath $serverStdout `
        -StderrPath $serverStderr

    Wait-Port -TargetHost $serverHost -Port $serverPort

    $clientExitCode = Invoke-Utf8ClientCommands `
        -ProjectRoot $projectRoot `
        -StdoutPath $clientStdout `
        -StderrPath $clientStderr

    if ($clientExitCode -ne 0) {
        $clientError = if (Test-Path $clientStderr) { Get-Content $clientStderr -Raw } else { "" }
        $clientOutput = if (Test-Path $clientStdout) { Get-Content $clientStdout -Raw } else { "" }
        throw "Client process failed with exit code $clientExitCode.`n$clientOutput`n$clientError"
    }

    $clientOutput = Get-Content $clientStdout -Raw
    Assert-OutputContains -Text $clientOutput -ExpectedFragments @(
        $serverEndpoint,
        "local_mcp_server",
        "Ping [ping]",
        "Echo [echo]"
    )

    Write-Output "E2E check passed."
    Write-Output ""
    Write-Output "Client output:"
    Write-Output $clientOutput
} finally {
    if ($serverProcess) {
        Stop-HeadlessLauncherProcess -Process $serverProcess
    }

    Stop-ListeningProcess -Port $serverPort
}
