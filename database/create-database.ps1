# ============================================
# Script PowerShell para Criar Banco MySQL
# Windows PowerShell Script
# ============================================

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Criar Banco de Dados MySQL" -ForegroundColor Cyan
Write-Host "API: Avaliar Média para Bônus" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Solicitar credenciais do MySQL
$mysqlUser = Read-Host "Digite o usuário MySQL (padrão: root)"
if ([string]::IsNullOrWhiteSpace($mysqlUser)) {
    $mysqlUser = "root"
}

$mysqlPassword = Read-Host "Digite a senha MySQL" -AsSecureString
$mysqlPasswordPlain = [Runtime.InteropServices.Marshal]::PtrToStringAuto(
    [Runtime.InteropServices.Marshal]::SecureStringToBSTR($mysqlPassword)
)

$mysqlHost = Read-Host "Digite o host MySQL (padrão: localhost)"
if ([string]::IsNullOrWhiteSpace($mysqlHost)) {
    $mysqlHost = "localhost"
}

$mysqlPort = Read-Host "Digite a porta MySQL (padrão: 3306)"
if ([string]::IsNullOrWhiteSpace($mysqlPort)) {
    $mysqlPort = "3306"
}

Write-Host ""
Write-Host "Executando scripts SQL..." -ForegroundColor Yellow

# Caminho dos scripts
$scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path
$script1 = Join-Path $scriptPath "01-create-database.sql"
$script2 = Join-Path $scriptPath "02-create-tables.sql"
$script3 = Join-Path $scriptPath "03-insert-test-data.sql"

# Executar scripts
try {
    Write-Host "1. Criando banco de dados..." -ForegroundColor Green
    $command1 = "mysql -h $mysqlHost -P $mysqlPort -u $mysqlUser -p$mysqlPasswordPlain < `"$script1`""
    Invoke-Expression $command1
    
    Write-Host "2. Criando tabelas..." -ForegroundColor Green
    $command2 = "mysql -h $mysqlHost -P $mysqlPort -u $mysqlUser -p$mysqlPasswordPlain < `"$script2`""
    Invoke-Expression $command2
    
    $insertData = Read-Host "Deseja inserir dados de teste? (S/N)"
    if ($insertData -eq "S" -or $insertData -eq "s") {
        Write-Host "3. Inserindo dados de teste..." -ForegroundColor Green
        $command3 = "mysql -h $mysqlHost -P $mysqlPort -u $mysqlUser -p$mysqlPasswordPlain < `"$script3`""
        Invoke-Expression $command3
    }
    
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Green
    Write-Host "Banco de dados criado com sucesso!" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "Banco: avaliar_media_bonus" -ForegroundColor Cyan
    Write-Host "Host: $mysqlHost" -ForegroundColor Cyan
    Write-Host "Porta: $mysqlPort" -ForegroundColor Cyan
    Write-Host ""
    
} catch {
    Write-Host ""
    Write-Host "ERRO ao executar scripts!" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    Write-Host ""
    Write-Host "Verifique:" -ForegroundColor Yellow
    Write-Host "1. MySQL está instalado e rodando" -ForegroundColor Yellow
    Write-Host "2. Usuário e senha estão corretos" -ForegroundColor Yellow
    Write-Host "3. Você tem permissão para criar bancos de dados" -ForegroundColor Yellow
}

# Limpar senha da memória
$mysqlPasswordPlain = $null

