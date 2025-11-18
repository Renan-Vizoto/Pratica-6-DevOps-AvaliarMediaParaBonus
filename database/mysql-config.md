# Configuração MySQL para a API

## 📋 Pré-requisitos
- MySQL 8.0 ou superior instalado
- Acesso ao MySQL com privilégios de criação de banco de dados

## 🚀 Passos para Configuração

### 1. Criar o Banco de Dados

Execute o script SQL fornecido:
```bash
mysql -u root -p < database/mysql-schema.sql
```

Ou acesse o MySQL e execute:
```sql
source database/mysql-schema.sql;
```

### 2. Atualizar application.properties

Substitua as configurações do H2 por MySQL:

```properties
# Configurações do MySQL Database
spring.datasource.url=jdbc:mysql://localhost:3306/avaliar_media_bonus?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=sua_senha_aqui

# Configurações JPA/Hibernate para MySQL
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Desabilitar H2 Console
spring.h2.console.enabled=false
```

### 3. Adicionar Dependência MySQL no pom.xml

Se ainda não estiver no pom.xml, adicione:

```xml
<!-- MySQL Connector -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

### 4. Estrutura das Tabelas

#### Tabela: `aluno`
- `id` (BIGINT, PK, AUTO_INCREMENT)
- `nome` (VARCHAR(100), NOT NULL)
- `email` (VARCHAR(100), NOT NULL, UNIQUE)

#### Tabela: `avaliacao`
- `id` (BIGINT, PK, AUTO_INCREMENT)
- `aluno_id` (BIGINT, FK, NOT NULL, UNIQUE)
- `media_final` (DOUBLE)

#### Tabela: `avaliacao_notas`
- `avaliacao_id` (BIGINT, FK, NOT NULL)
- `nota` (DOUBLE, NOT NULL)

## 🔗 Relacionamentos

- **Aluno 1:1 Avaliacao**: Um aluno tem uma única avaliação
- **Avaliacao 1:N AvaliacaoNotas**: Uma avaliação tem várias notas

## 📝 Notas Importantes

1. O banco usa `utf8mb4` para suportar caracteres especiais
2. Engine InnoDB para suportar Foreign Keys
3. `ON DELETE CASCADE` para manter integridade referencial
4. `spring.jpa.hibernate.ddl-auto=validate` em produção (não cria/apaga tabelas automaticamente)

