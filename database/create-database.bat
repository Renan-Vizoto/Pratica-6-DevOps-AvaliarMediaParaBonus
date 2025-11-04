@echo off
REM ============================================
REM Script Batch para Criar Banco MySQL
REM Windows Batch Script
REM ============================================

echo ========================================
echo Criar Banco de Dados MySQL
echo API: Avaliar Media para Bonus
echo ========================================
echo.

set /p MYSQL_USER="Digite o usuario MySQL (padrao: root): "
if "%MYSQL_USER%"=="" set MYSQL_USER=root

set /p MYSQL_PASSWORD="Digite a senha MySQL: "
set /p MYSQL_HOST="Digite o host MySQL (padrao: localhost): "
if "%MYSQL_HOST%"=="" set MYSQL_HOST=localhost

set /p MYSQL_PORT="Digite a porta MySQL (padrao: 3306): "
if "%MYSQL_PORT%"=="" set MYSQL_PORT=3306

echo.
echo Executando scripts SQL...
echo.

echo 1. Criando banco de dados...
mysql -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% < "%~dp0\01-create-database.sql"
if errorlevel 1 (
    echo ERRO ao criar banco de dados!
    pause
    exit /b 1
)

echo 2. Criando tabelas...
mysql -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% < "%~dp0\02-create-tables.sql"
if errorlevel 1 (
    echo ERRO ao criar tabelas!
    pause
    exit /b 1
)

set /p INSERT_DATA="Deseja inserir dados de teste? (S/N): "
if /i "%INSERT_DATA%"=="S" (
    echo 3. Inserindo dados de teste...
    mysql -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% < "%~dp0\03-insert-test-data.sql"
    if errorlevel 1 (
        echo ERRO ao inserir dados!
        pause
        exit /b 1
    )
)

echo.
echo ========================================
echo Banco de dados criado com sucesso!
echo ========================================
echo.
echo Banco: avaliar_media_bonus
echo Host: %MYSQL_HOST%
echo Porta: %MYSQL_PORT%
echo.
pause

