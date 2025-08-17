@echo off
echo Baixando dependencias JUnit...

REM Criar diretório lib se não existir
if not exist "lib" mkdir lib

REM Download JUnit 5
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter/5.10.0/junit-jupiter-5.10.0.jar' -OutFile 'lib/junit-jupiter-5.10.0.jar'"
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/5.10.0/junit-jupiter-api-5.10.0.jar' -OutFile 'lib/junit-jupiter-api-5.10.0.jar'"
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-engine/5.10.0/junit-jupiter-engine-5.10.0.jar' -OutFile 'lib/junit-jupiter-engine-5.10.0.jar'"
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/junit/platform/junit-platform-commons/1.10.0/junit-platform-commons-1.10.0.jar' -OutFile 'lib/junit-platform-commons-1.10.0.jar'"
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/junit/platform/junit-platform-engine/1.10.0/junit-platform-engine-1.10.0.jar' -OutFile 'lib/junit-platform-engine-1.10.0.jar'"

echo Dependencias baixadas com sucesso!
pause
