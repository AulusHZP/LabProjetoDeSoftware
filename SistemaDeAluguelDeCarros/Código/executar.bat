@echo off
echo ========================================
echo Sistema de Aluguel de Carros
echo ========================================
echo.

echo Verificando Java...
java -version
echo.

echo Tentando executar a aplicacao...
echo.

REM Tentar executar o JAR diretamente
if exist "target\sistema-aluguel-carros-1.0-SNAPSHOT.war" (
    echo Executando JAR encontrado...
    java -jar target\sistema-aluguel-carros-1.0-SNAPSHOT.war
) else (
    echo JAR nao encontrado. Tentando compilar...
    
    REM Verificar se Maven esta disponivel
    where mvn >nul 2>&1
    if %errorlevel% == 0 (
        echo Maven encontrado. Compilando...
        mvn clean compile
        mvn spring-boot:run
    ) else (
        echo Maven nao encontrado. Instalando Maven Wrapper...
        
        REM Baixar Maven Wrapper se nao existir
        if not exist "mvnw.cmd" (
            echo Baixando Maven Wrapper...
            curl -o mvnw.cmd https://raw.githubusercontent.com/takari/maven-wrapper/master/mvnw.cmd
            curl -o mvnw https://raw.githubusercontent.com/takari/maven-wrapper/master/mvnw
        )
        
        if exist "mvnw.cmd" (
            echo Executando com Maven Wrapper...
            mvnw.cmd clean spring-boot:run
        ) else (
            echo Erro: Nao foi possivel executar a aplicacao.
            echo Por favor, instale o Maven ou use uma IDE como IntelliJ IDEA ou Eclipse.
        )
    )
)

echo.
echo ========================================
echo Aplicacao finalizada.
echo ========================================
pause
