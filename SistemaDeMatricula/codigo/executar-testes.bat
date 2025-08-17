@echo off
echo Executando testes do Sistema de Matriculas...
echo.

REM Verifica se o Maven está disponível
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo Maven nao encontrado. Tentando usar Maven Wrapper...
    if exist "mvnw.cmd" (
        call mvnw.cmd test
    ) else (
        echo Maven nao encontrado e Maven Wrapper nao disponivel.
        echo Por favor, instale o Maven ou use uma IDE que suporte JUnit.
        pause
        exit /b 1
    )
) else (
    mvn test
)

echo.
echo Testes concluidos!
pause
