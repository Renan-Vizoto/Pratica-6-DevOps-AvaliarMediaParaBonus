@echo off
REM Script para testar a aplicação gamification-clean-ddd (Windows)
REM Autor: Equipe de Desenvolvimento
REM Data: 2024

echo ==========================================
echo   Teste da Aplicacao - Kafka Integration
echo ==========================================
echo.

REM Verificar pré-requisitos
echo Verificando pre-requisitos...
where java >nul 2>&1
if %errorlevel% neq 0 (
    echo Erro: Java nao esta instalado
    exit /b 1
)

where mvn >nul 2>&1
if %errorlevel% neq 0 (
    echo Erro: Maven nao esta instalado
    exit /b 1
)

where docker >nul 2>&1
if %errorlevel% neq 0 (
    echo Erro: Docker nao esta instalado
    exit /b 1
)

echo [OK] Pre-requisitos verificados
echo.

REM Navegar para o diretório do projeto
cd /d "%~dp0\.."
set PROJECT_DIR=%CD%
echo Diretorio do projeto: %PROJECT_DIR%
echo.

REM 1. Limpar e compilar
echo ==========================================
echo 1. Executando mvn clean install...
echo ==========================================
call mvn clean install

if %errorlevel% neq 0 (
    echo Erro ao executar mvn clean install
    exit /b 1
)

echo [OK] Build concluido com sucesso
echo.

REM 2. Verificar relatório Jacoco
echo ==========================================
echo 2. Relatorio Jacoco gerado em:
echo    %PROJECT_DIR%\target\site\jacoco\index.html
echo ==========================================
echo.

REM 3. Subir containers Docker
echo ==========================================
echo 3. Subindo containers Docker...
echo ==========================================
docker-compose up -d

if %errorlevel% neq 0 (
    echo Erro ao subir containers Docker
    exit /b 1
)

echo [OK] Containers Docker iniciados
echo.

REM Aguardar containers iniciarem
echo Aguardando containers iniciarem (10 segundos)...
timeout /t 10 /nobreak >nul

REM Verificar containers
echo Containers rodando:
docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}" | findstr /i "kafka rabbitmq mqtt mongo"
echo.

REM 4. Executar aplicação
echo ==========================================
echo 4. Para executar a aplicacao, use:
echo    mvn spring-boot:run
echo.
echo    Em outro terminal, teste o endpoint:
echo    curl -X POST http://localhost:8080/api/gamification/courses/completion ^
echo      -H "Content-Type: application/json" ^
echo      -d "{\"alunoId\": 1, \"nomeCurso\": \"Teste\", \"mediaFinal\": 8.5, \"ajudouOutros\": true}"
echo ==========================================
echo.

echo [OK] Script concluido!
echo.
echo Proximos passos:
echo 1. Execute 'mvn spring-boot:run' em outro terminal
echo 2. Teste o endpoint conforme mostrado acima
echo 3. Verifique os logs para ver eventos no Kafka

pause

