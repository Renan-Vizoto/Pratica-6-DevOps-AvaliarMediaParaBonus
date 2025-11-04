# 📋 Comandos para Criar o Banco de Dados MySQL

## 🚀 Opção 1: Usando Scripts SQL (Recomendado)

### **Passo 1: Criar o Banco de Dados**
```bash
mysql -u root -p < database/01-create-database.sql
```

### **Passo 2: Criar as Tabelas**
```bash
mysql -u root -p < database/02-create-tables.sql
```

### **Passo 3: Inserir Dados de Teste (Opcional)**
```bash
mysql -u root -p < database/03-insert-test-data.sql
```

### **Passo 4: Executar Consultas Úteis**
```bash
mysql -u root -p < database/04-useful-queries.sql
```

---

## 🔧 Opção 2: Usando PowerShell (Windows)

### **Executar Script Automatizado**
```powershell
cd database
.\create-database.ps1
```

O script irá:
1. Solicitar credenciais do MySQL
2. Executar todos os scripts automaticamente
3. Perguntar se deseja inserir dados de teste

---

## 📝 Opção 3: Comandos SQL Manuais (Linha de Comando)

### **Acessar MySQL**
```bash
mysql -u root -p
```

### **Dentro do MySQL, execute:**

```sql
-- 1. Criar banco
CREATE DATABASE IF NOT EXISTS avaliar_media_bonus 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

-- 2. Usar o banco
USE avaliar_media_bonus;

-- 3. Criar tabela aluno
CREATE TABLE IF NOT EXISTS aluno (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. Criar tabela avaliacao
CREATE TABLE IF NOT EXISTS avaliacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    aluno_id BIGINT NOT NULL UNIQUE,
    media_final DOUBLE,
    FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON DELETE CASCADE,
    INDEX idx_aluno_id (aluno_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5. Criar tabela avaliacao_notas
CREATE TABLE IF NOT EXISTS avaliacao_notas (
    avaliacao_id BIGINT NOT NULL,
    nota DOUBLE NOT NULL,
    FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id) ON DELETE CASCADE,
    INDEX idx_avaliacao_id (avaliacao_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 6. Verificar tabelas criadas
SHOW TABLES;
```

---

## 🎯 Opção 4: Executar Script Completo de Uma Vez

```bash
mysql -u root -p < database/mysql-schema.sql
```

Este comando executa tudo de uma vez (banco + tabelas + dados de teste).

---

## ✅ Verificar se o Banco foi Criado

### **Listar bancos**
```sql
SHOW DATABASES;
```

### **Usar o banco**
```sql
USE avaliar_media_bonus;
```

### **Listar tabelas**
```sql
SHOW TABLES;
```

### **Ver estrutura de uma tabela**
```sql
DESCRIBE aluno;
DESCRIBE avaliacao;
DESCRIBE avaliacao_notas;
```

### **Ver dados inseridos**
```sql
SELECT * FROM aluno;
SELECT * FROM avaliacao;
SELECT * FROM avaliacao_notas;
```

---

## 🔍 Consultas Úteis

### **Ver alunos com status de bônus**
```sql
SELECT 
    a.id,
    a.nome,
    a.email,
    av.media_final,
    CASE 
        WHEN av.media_final > 7.0 THEN 'Elegível (3 cursos)'
        ELSE 'Não elegível (0 cursos)'
    END AS status_bonus
FROM aluno a
LEFT JOIN avaliacao av ON a.id = av.aluno_id;
```

### **Ver notas de uma avaliação**
```sql
SELECT 
    av.id AS avaliacao_id,
    a.nome AS aluno_nome,
    av.media_final,
    GROUP_CONCAT(an.nota SEPARATOR ', ') AS notas
FROM avaliacao av
JOIN aluno a ON av.aluno_id = a.id
LEFT JOIN avaliacao_notas an ON av.id = an.avaliacao_id
GROUP BY av.id, a.nome, av.media_final;
```

---

## 🗑️ Limpar Banco (Se Necessário)

```sql
USE avaliar_media_bonus;

-- Limpar dados
DELETE FROM avaliacao_notas;
DELETE FROM avaliacao;
DELETE FROM aluno;

-- Ou deletar o banco completamente
DROP DATABASE avaliar_media_bonus;
```

---

## 📌 Notas Importantes

1. **Requisitos:**
   - MySQL 8.0 ou superior
   - Usuário com permissão para criar bancos de dados

2. **Credenciais:**
   - Usuário padrão: `root`
   - Senha: (sua senha do MySQL)

3. **Estrutura:**
   - Banco: `avaliar_media_bonus`
   - Charset: `utf8mb4`
   - Engine: `InnoDB`

4. **Segurança:**
   - Não commite senhas no código
   - Use variáveis de ambiente em produção

---

## 🆘 Troubleshooting

### **Erro: Access denied**
```bash
# Verificar se MySQL está rodando
sudo service mysql status  # Linux
# ou
net start MySQL80  # Windows
```

### **Erro: Cannot connect to MySQL**
```bash
# Verificar se MySQL está na porta 3306
netstat -an | findstr 3306  # Windows
```

### **Erro: Database already exists**
```sql
-- Deletar banco existente
DROP DATABASE IF EXISTS avaliar_media_bonus;

-- Recriar
CREATE DATABASE avaliar_media_bonus;
```

