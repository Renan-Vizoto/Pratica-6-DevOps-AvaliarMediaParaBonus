# 🧪 Como Executar os Testes

Este guia explica como executar os testes unitários e de integração do projeto.

## 📋 Índice
1. [Executar no Eclipse](#executar-no-eclipse)
2. [Executar via Maven (Terminal)](#executar-via-maven-terminal)
3. [Executar Testes Específicos](#executar-testes-específicos)
4. [Verificar Cobertura de Código](#verificar-cobertura-de-código)

---

## 🖥️ Executar no Eclipse

### Método 1: Executar Todos os Testes do Projeto

1. **Clique com botão direito** no projeto `Pratica-6-DevOps-AvaliarMediaParaBonus`
2. Selecione **Run As** → **JUnit Test**
3. Ou use o atalho: `Alt + Shift + X, T`

### Método 2: Executar uma Classe de Teste Específica

1. Abra o arquivo de teste (ex: `AlunoControllerTest.java`)
2. **Clique com botão direito** no arquivo
3. Selecione **Run As** → **JUnit Test**
4. Ou use o atalho: `Alt + Shift + X, T`

### Método 3: Executar um Método de Teste Específico

1. Abra o arquivo de teste
2. Clique dentro do método de teste (ex: `deveCriarAlunoComSucesso()`)
3. **Clique com botão direito** → **Run As** → **JUnit Test**
4. Ou use o atalho: `Alt + Shift + X, T`

### Método 4: Executar via Menu Run

1. Vá em **Run** → **Run Configurations...**
2. Clique em **JUnit** → **New Configuration**
3. Configure:
   - **Name**: Nome da configuração
   - **Test class**: Selecione a classe de teste
   - **Test method**: (opcional) Selecione um método específico
4. Clique em **Run**

### Método 5: Executar via View JUnit

1. Vá em **Window** → **Show View** → **Other...**
2. Selecione **JUnit** → **JUnit**
3. Na view JUnit, clique no botão **Run All Tests** (▶️)
4. Ou selecione testes específicos e clique em **Run**

---

## 💻 Executar via Maven (Terminal)

### Pré-requisitos
- Maven instalado e configurado no PATH
- Terminal/Prompt de Comando aberto na raiz do projeto

### Comandos Maven

#### 1. Executar TODOS os Testes
```bash
mvn test
```

#### 2. Executar Testes e Pular Compilação (se já compilado)
```bash
mvn surefire:test
```

#### 3. Executar Testes com Output Detalhado
```bash
mvn test -X
```

#### 4. Executar Testes e Gerar Relatório
```bash
mvn test surefire-report:report
```
O relatório será gerado em: `target/site/surefire-report.html`

#### 5. Executar Testes Específicos por Classe
```bash
mvn test -Dtest=AlunoControllerTest
```

#### 6. Executar Testes Específicos por Método
```bash
mvn test -Dtest=AlunoControllerTest#deveCriarAlunoComSucesso
```

#### 7. Executar Múltiplos Testes
```bash
mvn test -Dtest=AlunoControllerTest,AvaliacaoControllerTest
```

#### 8. Executar Testes por Padrão (regex)
```bash
mvn test -Dtest="*ControllerTest"
```

#### 9. Executar Testes e Ignorar Falhas (continuar execução)
```bash
mvn test -Dmaven.test.failure.ignore=true
```

#### 10. Executar Testes com Cobertura (JaCoCo)
```bash
mvn clean test jacoco:report
```
O relatório será gerado em: `target/site/jacoco/index.html`

---

## 🎯 Executar Testes Específicos

### No Eclipse

#### Por Pacote
1. Clique com botão direito no pacote (ex: `controller`)
2. **Run As** → **JUnit Test**

#### Por Suite de Testes
1. Crie uma classe `@Suite`:
```java
@Suite
@SelectClasses({AlunoControllerTest.class, AvaliacaoControllerTest.class})
public class ControllerTestSuite {}
```
2. Execute a suite normalmente

### Via Maven

#### Testes Unitários de Controller
```bash
mvn test -Dtest="*ControllerTest"
```

#### Testes de Repository
```bash
mvn test -Dtest="*JpaRepositoryTest"
```

#### Testes de Entity
```bash
mvn test -Dtest="*Test" -Dtest.includes="**/entity/*Test"
```

#### Testes de Service
```bash
mvn test -Dtest="*ServiceTest"
```

#### Testes de Integração
```bash
mvn test -Dtest="*IntegrationTest"
```

---

## 📊 Verificar Cobertura de Código

### No Eclipse com EclEmma

1. **Instale o EclEmma** (se não tiver):
   - Help → Eclipse Marketplace
   - Busque "EclEmma"
   - Instale

2. **Execute com Cobertura**:
   - Clique com botão direito no projeto
   - **Coverage As** → **JUnit Test**

3. **Visualize o Relatório**:
   - Abra a view **Coverage**
   - Veja a cobertura por classe/método

### Via Maven com JaCoCo

```bash
# Executar testes e gerar relatório de cobertura
mvn clean test jacoco:report

# Abrir relatório (Windows)
start target/site/jacoco/index.html

# Abrir relatório (Linux/Mac)
open target/site/jacoco/index.html
```

---

## 🔍 Troubleshooting

### Problema: Testes não executam no Eclipse

**Solução:**
1. Verifique se o projeto está como **Java Project**
2. Clique com botão direito → **Properties** → **Java Build Path**
3. Verifique se `JUnit 5` está nas bibliotecas
4. Se não estiver, adicione: **Add Library** → **JUnit** → **JUnit 5**

### Problema: Maven não encontra testes

**Solução:**
```bash
# Limpar e recompilar
mvn clean compile test-compile

# Executar novamente
mvn test
```

### Problema: Testes falham por banco de dados

**Solução:**
- Testes unitários (`@WebMvcTest`, `@Mock`) não precisam de banco
- Testes de integração (`@SpringBootTest`) usam H2 em memória
- Verifique `application-test.properties` está configurado

### Problema: Testes muito lentos

**Solução:**
- Execute apenas testes unitários: `mvn test -Dtest="*Test" -Dtest.excludes="*IntegrationTest"`
- Use `@WebMvcTest` ao invés de `@SpringBootTest` quando possível

---

## 📝 Exemplos Práticos

### Executar apenas testes unitários de Controller
```bash
mvn test -Dtest="*ControllerTest"
```

### Executar todos os testes exceto integração
```bash
mvn test -Dtest.excludes="*IntegrationTest"
```

### Executar testes e ver output em tempo real
```bash
mvn test -Dtest="*ControllerTest" | grep -E "(Tests run|FAILURE|SUCCESS)"
```

### Executar testes em paralelo (Maven 3.x)
```bash
mvn test -T 4  # 4 threads
```

---

## ✅ Checklist de Execução

- [ ] Projeto compilado sem erros
- [ ] Dependências Maven baixadas (`mvn clean install`)
- [ ] JUnit 5 configurado no Eclipse
- [ ] `application-test.properties` configurado
- [ ] Testes executam individualmente
- [ ] Testes executam em lote
- [ ] Relatórios gerados corretamente

---

## 🎓 Dicas

1. **Use atalhos do Eclipse**: `Alt + Shift + X, T` para executar testes rapidamente
2. **Execute testes antes de commitar**: `mvn test` garante que tudo está funcionando
3. **Use testes específicos durante desenvolvimento**: economiza tempo
4. **Monitore cobertura**: mantenha acima de 70%
5. **Execute testes de integração separadamente**: são mais lentos

---

## 📚 Recursos Adicionais

- [Documentação JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
- [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)

