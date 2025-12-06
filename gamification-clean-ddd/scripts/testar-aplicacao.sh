#!/bin/bash

# Script para testar a aplicação gamification-clean-ddd
# Autor: Equipe de Desenvolvimento
# Data: 2024

echo "=========================================="
echo "  Teste da Aplicação - Kafka Integration"
echo "=========================================="
echo ""

# Cores para output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Função para verificar se comando existe
check_command() {
    if ! command -v $1 &> /dev/null; then
        echo -e "${RED}Erro: $1 não está instalado${NC}"
        exit 1
    fi
}

# Verificar pré-requisitos
echo "Verificando pré-requisitos..."
check_command java
check_command mvn
check_command docker

echo -e "${GREEN}✓ Pré-requisitos OK${NC}"
echo ""

# Navegar para o diretório do projeto
cd "$(dirname "$0")/.." || exit
PROJECT_DIR=$(pwd)
echo "Diretório do projeto: $PROJECT_DIR"
echo ""

# 1. Limpar e compilar
echo "=========================================="
echo "1. Executando mvn clean install..."
echo "=========================================="
mvn clean install

if [ $? -ne 0 ]; then
    echo -e "${RED}Erro ao executar mvn clean install${NC}"
    exit 1
fi

echo -e "${GREEN}✓ Build concluído com sucesso${NC}"
echo ""

# 2. Verificar relatório Jacoco
echo "=========================================="
echo "2. Relatório Jacoco gerado em:"
echo "   $PROJECT_DIR/target/site/jacoco/index.html"
echo "=========================================="
echo ""

# 3. Subir containers Docker
echo "=========================================="
echo "3. Subindo containers Docker..."
echo "=========================================="
docker-compose up -d

if [ $? -ne 0 ]; then
    echo -e "${RED}Erro ao subir containers Docker${NC}"
    exit 1
fi

echo -e "${GREEN}✓ Containers Docker iniciados${NC}"
echo ""

# Aguardar containers iniciarem
echo "Aguardando containers iniciarem (10 segundos)..."
sleep 10

# Verificar containers
echo "Containers rodando:"
docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}" | grep -E "kafka|rabbitmq|mqtt|mongo"
echo ""

# 4. Executar aplicação
echo "=========================================="
echo "4. Para executar a aplicação, use:"
echo "   mvn spring-boot:run"
echo ""
echo "   Em outro terminal, teste o endpoint:"
echo "   curl -X POST http://localhost:8080/api/gamification/courses/completion \\"
echo "     -H \"Content-Type: application/json\" \\"
echo "     -d '{\"alunoId\": 1, \"nomeCurso\": \"Teste\", \"mediaFinal\": 8.5, \"ajudouOutros\": true}'"
echo "=========================================="
echo ""

echo -e "${GREEN}Script concluído!${NC}"
echo ""
echo "Próximos passos:"
echo "1. Execute 'mvn spring-boot:run' em outro terminal"
echo "2. Teste o endpoint conforme mostrado acima"
echo "3. Verifique os logs para ver eventos no Kafka"

