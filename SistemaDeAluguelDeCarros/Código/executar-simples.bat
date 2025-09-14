@echo off
echo ========================================
echo Sistema de Aluguel de Carros
echo Executando sem Maven
echo ========================================
echo.

echo Verificando Java...
java -version
echo.

echo Compilando classes...
if not exist "target\classes" mkdir target\classes

REM Compilar as classes Java
javac -cp "target\lib\*" -d target\classes src\main\java\org\example\*.java src\main\java\org\example\model\*.java src\main\java\org\example\repository\*.java src\main\java\org\example\service\*.java src\main\java\org\example\controller\*.java

if %errorlevel% neq 0 (
    echo Erro na compilacao. Verifique se todas as dependencias estao disponiveis.
    pause
    exit /b 1
)

echo.
echo Executando aplicacao...
echo Acesse: http://localhost:8080/View/index.html
echo.

REM Executar a aplicacao
java -cp "target\classes;target\lib\*" org.example.Application

echo.
echo ========================================
echo Aplicacao finalizada.
echo ========================================
pause

