Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

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

@($serverStdout, $serverStderr, $clientStdout, $clientStderr) | ForEach-Object {
    if (Test-Path $_) {
        Remove-Item $_ -Force
    }
}

$serverProcess = $null

try {
    Stop-ListeningProcess -Port $serverPort

    $serverProcess = Start-Process `
        -FilePath ".\gradlew.bat" `
        -ArgumentList "runServer", "--no-daemon" `
        -WorkingDirectory $projectRoot `
        -RedirectStandardOutput $serverStdout `
        -RedirectStandardError $serverStderr `
        -PassThru

    Wait-Port -TargetHost $serverHost -Port $serverPort

    & cmd /c ".\gradlew.bat runClient --no-daemon 2>&1" | Tee-Object -FilePath $clientStdout
    $clientExitCode = $LASTEXITCODE

    if ($clientExitCode -ne 0) {
        $clientError = if (Test-Path $clientStdout) { Get-Content $clientStdout -Raw } else { "" }
        throw "Client process failed with exit code $clientExitCode.`n$clientError"
    }

    $clientOutput = Get-Content $clientStdout -Raw
    Assert-OutputContains -Text $clientOutput -ExpectedFragments @(
        "Connected to MCP server: $serverEndpoint",
        "Server name: local_mcp_server",
        "Available tools (2):",
        "Ping [ping]",
        "Echo [echo]"
    )

    Write-Output "E2E check passed."
    Write-Output ""
    Write-Output "Client output:"
    Write-Output $clientOutput
} finally {
    if ($serverProcess -and -not $serverProcess.HasExited) {
        Stop-Process -Id $serverProcess.Id -Force -ErrorAction SilentlyContinue
    }

    Stop-ListeningProcess -Port $serverPort
}
