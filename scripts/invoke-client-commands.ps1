param(
    [string[]]$Commands = @("connect", "exit"),
    [string]$ProjectRoot = (Split-Path -Parent $PSScriptRoot),
    [string]$StdoutPath = "",
    [string]$StderrPath = ""
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"
[Console]::InputEncoding = [System.Text.Encoding]::UTF8
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$OutputEncoding = [System.Text.Encoding]::UTF8

function Invoke-InteractiveClientCommands {
    param(
        [Parameter(Mandatory = $true)]
        [string]$WorkingDirectory,

        [Parameter(Mandatory = $true)]
        [string[]]$InputCommands
    )

    $psi = [System.Diagnostics.ProcessStartInfo]::new()
    $psi.FileName = "cmd.exe"
    $psi.Arguments = "/c chcp 65001>nul && .\\gradlew.bat runClient --no-daemon"
    $psi.WorkingDirectory = $WorkingDirectory
    $psi.UseShellExecute = $false
    $psi.RedirectStandardInput = $true
    $psi.RedirectStandardOutput = $true
    $psi.RedirectStandardError = $true
    $psi.StandardOutputEncoding = [System.Text.Encoding]::UTF8
    $psi.StandardErrorEncoding = [System.Text.Encoding]::UTF8

    $process = [System.Diagnostics.Process]::new()
    $process.StartInfo = $psi

    try {
        $process.Start() | Out-Null

        foreach ($command in $InputCommands) {
            $process.StandardInput.WriteLine($command)
        }

        $process.StandardInput.Close()

        $stdout = $process.StandardOutput.ReadToEnd()
        $stderr = $process.StandardError.ReadToEnd()
        $process.WaitForExit()

        return [PSCustomObject]@{
            ExitCode = $process.ExitCode
            Stdout = $stdout
            Stderr = $stderr
        }
    } finally {
        $process.Dispose()
    }
}

Set-Location $ProjectRoot

$result = Invoke-InteractiveClientCommands `
    -WorkingDirectory $ProjectRoot `
    -InputCommands $Commands

if ($StdoutPath) {
    [System.IO.File]::WriteAllText($StdoutPath, $result.Stdout, [System.Text.Encoding]::UTF8)
}

if ($StderrPath) {
    [System.IO.File]::WriteAllText($StderrPath, $result.Stderr, [System.Text.Encoding]::UTF8)
}

if ($result.Stdout) {
    [Console]::Out.Write($result.Stdout)
}

if ($result.Stderr) {
    [Console]::Out.Write($result.Stderr)
}

exit $result.ExitCode
