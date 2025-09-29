#!/usr/bin/env pwsh
# Start backend JAR: try port 8080, fall back to 8081 if 8080 is in use.
# Uses application.yml + application-postgres.yml and disables Hibernate DDL changes.

param()

$ErrorActionPreference = 'Stop'

# Resolve paths
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
$targetDir = Join-Path $scriptDir 'target'

Write-Output "Looking for JAR in: $targetDir"
$jar = Get-ChildItem -Path $targetDir -Filter '*.jar' -File -ErrorAction SilentlyContinue |
    Where-Object { $_.Name -like '*sistema-aluguel-carros*' } |
    Sort-Object LastWriteTime -Descending | Select-Object -First 1

if (-not $jar) {
    Write-Error "Could not find backend JAR under $targetDir. Build the project first (mvn -DskipTests package)."
    exit 1
}

function Test-PortFree($port) {
    try {
        $conn = Get-NetTCPConnection -LocalPort $port -ErrorAction SilentlyContinue
        return -not [bool]$conn
    } catch {
        # Older systems may not have Get-NetTCPConnection; try Test-NetConnection
        $res = Test-NetConnection -ComputerName 'localhost' -Port $port -WarningAction SilentlyContinue
        return -not $res.TcpTestSucceeded
    }
}

$preferredPorts = @(8080, 8081)
$portToUse = $null
foreach ($p in $preferredPorts) {
    if (Test-PortFree $p) { $portToUse = $p; break }
}

if (-not $portToUse) {
    Write-Warning "Neither 8080 nor 8081 appear free. You can still force a port with --server.port or free the existing service. Proceeding to start on 8081 anyway."
    $portToUse = 8081
}

$args = @('-jar', """$($jar.FullName)""", '--spring.config.location=classpath:/application.yml,classpath:/application-postgres.yml', '--spring.jpa.hibernate.ddl-auto=none', "--server.port=$portToUse")

Write-Output "Starting backend: java $($args -join ' ')"

# Start process in a new window so logs stay visible; use -PassThru to show PID
try {
    $proc = Start-Process -FilePath 'java' -ArgumentList $args -NoNewWindow -PassThru
    Write-Output "Started process Id=$($proc.Id), listening on port $portToUse (if the app starts successfully)."
} catch {
    Write-Error "Failed to start process: $_"
    exit 1
}
