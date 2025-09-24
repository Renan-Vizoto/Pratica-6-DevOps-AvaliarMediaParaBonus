# Projeto - Recompensa por Desempenho em Cursos

## Integrantes
- João Victor de Oliveira - 212106  
- Renan Vizoto Ferreira - 222220  
- Emily Kaori Uehara Soares - 235470  

---

## 📝 User Story
**EU COMO** aluno,  
**PRECISO/QUERO** ser recompensado,  
**PARA** acessar mais 3 cursos a cada curso finalizado com média acima de **7,0**.  

---

## ✅ BDDs (Behavior Driven Development)

### Cenário 1 - Concessão de cursos adicionais - Emily
**Dado que** um aluno com assinatura **"Básico"** concluiu um curso  
**E** sua média final foi calculada  
**Quando** sua média for **acima de 7.0**  
**Então** ele deve receber o direito de realizar **mais 3 cursos**  

---

### Cenário 2 - Média igual a 7,0 - João
**Dado que** eu sou um aluno com assinatura **"Básica"**  
**E** estava cursando **"UI/UX"**  
**Quando** finalizo todas as atividades e concluo o curso  
**E** o sistema calcula minha média como **7.0**  
**Então** minha conta **não recebe cursos adicionais**  

---

### Cenário 3 - Média abaixo de 7,0 - Renan
**Dado que** um aluno concluiu um curso  
**E** sua média final foi calculada  
**Quando** a média for **abaixo de 7,0**  
**Então** a quantidade de cursos disponíveis **não deve ser alterada**  
