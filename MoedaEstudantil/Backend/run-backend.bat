@echo off
echo Starting Moeda Estudantil Backend...
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Maven is not installed or not in PATH
    echo Please install Maven
    pause
    exit /b 1
)

echo Starting Spring Boot application...
echo Backend will be available at: http://localhost:8080
echo API documentation: http://localhost:8080/swagger-ui.html
echo.

mvn spring-boot:run

pause
