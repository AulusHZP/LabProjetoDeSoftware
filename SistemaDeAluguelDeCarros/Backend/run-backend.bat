@echo off
REM Wrapper to run the PowerShell script that starts the backend on 8080 or falls back to 8081
SET SCRIPT_DIR=%~dp0
powershell -NoProfile -ExecutionPolicy Bypass -File "%SCRIPT_DIR%run-backend.ps1"
