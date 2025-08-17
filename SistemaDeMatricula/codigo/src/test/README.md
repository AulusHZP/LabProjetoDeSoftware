# Testes Automatizados - Sistema de Matrículas

Este diretório contém os testes automatizados para o Sistema de Matrículas, desenvolvidos com JUnit 5 e Mockito.

## Estrutura dos Testes

### Testes Unitários

1. **UsuarioTest.java** - Testes para a classe base `Usuario`
   - Criação de usuário com dados corretos
   - Autenticação com senha correta e incorreta
   - Alteração de dados pessoais
   - Tratamento de valores nulos

2. **AlunoTest.java** - Testes para a classe `Aluno`
   - Criação de aluno com dados corretos
   - Matrícula em disciplinas obrigatórias e optativas
   - Respeito aos limites de disciplinas (4 obrigatórias, 2 optativas)
   - Cancelamento de matrículas
   - Separação entre disciplinas obrigatórias e optativas

3. **ProfessorTest.java** - Testes para a classe `Professor`
   - Criação de professor com dados corretos
   - Adição e remoção de disciplinas ministradas
   - Herança de funcionalidades de autenticação
   - Tratamento de disciplinas nulas

4. **DisciplinaTest.java** - Testes para a classe `Disciplina`
   - Criação de disciplina
   - Operações de adicionar e remover alunos (implementação atual)
   - Verificação de ativação
   - Tratamento de parâmetros nulos

5. **SistemaMatriculasTest.java** - Testes para a classe `SistemaMatriculas`
   - Criação do sistema
   - Operações de login (implementação atual)
   - Efetivação e cancelamento de matrículas
   - Encerramento de período
   - Tratamento de parâmetros nulos

6. **CursoTest.java** - Testes para a classe `Curso`
   - Criação de curso
   - Adição de disciplinas
   - Tratamento de disciplinas nulas

### Testes de Integração

7. **SistemaIntegracaoTest.java** - Testes de integração do sistema
   - Integração entre todas as classes
   - Fluxo completo de matrícula
   - Cancelamento de matrículas
   - Respeito aos limites de matrícula
   - Múltiplos alunos no sistema
   - Professor ministrando múltiplas disciplinas
   - Autenticação de diferentes tipos de usuário
   - Encerramento de período
   - Consistência entre operações

### Testes de Performance

8. **SistemaPerformanceTest.java** - Testes de performance
   - Múltiplas matrículas de alunos (100 alunos)
   - Múltiplos cancelamentos de matrícula
   - Professor ministrando múltiplas disciplinas (50 disciplinas)
   - Múltiplas autenticações (100 autenticações)
   - Operações concorrentes de matrícula
   - Múltiplos encerramentos de período

## Como Executar os Testes

### Pré-requisitos
- Java 21 ou superior
- Maven 3.6 ou superior

### Executando todos os testes
```bash
mvn test
```

### Executando testes específicos
```bash
# Executar apenas testes unitários
mvn test -Dtest="*Test"

# Executar apenas testes de integração
mvn test -Dtest="*IntegracaoTest"

# Executar apenas testes de performance
mvn test -Dtest="*PerformanceTest"

# Executar teste específico
mvn test -Dtest="AlunoTest"
```

### Executando com relatório detalhado
```bash
mvn test -Dsurefire.useFile=false
```

## Cobertura de Testes

Os testes cobrem:

### Funcionalidades Implementadas
- ✅ Criação de usuários (Aluno, Professor)
- ✅ Autenticação de usuários
- ✅ Matrícula de alunos em disciplinas
- ✅ Cancelamento de matrículas
- ✅ Limites de disciplinas por aluno
- ✅ Gerenciamento de disciplinas ministradas por professor
- ✅ Alteração de dados pessoais

### Funcionalidades com Implementação Parcial (TODO)
- ⚠️ Sistema de login (retorna null)
- ⚠️ Efetivação de matrículas no sistema (método vazio)
- ⚠️ Cancelamento de matrículas no sistema (método vazio)
- ⚠️ Encerramento de período (método vazio)
- ⚠️ Adição/remoção de alunos em disciplinas (retorna false)
- ⚠️ Verificação de ativação de disciplinas (método vazio)
- ⚠️ Gerenciamento de disciplinas em cursos (método vazio)

## Padrões de Teste Utilizados

### Estrutura dos Testes
- **@BeforeEach**: Configuração inicial para cada teste
- **@Test**: Métodos de teste individuais
- **@DisplayName**: Descrições legíveis dos testes
- **Assertions**: Verificações específicas do comportamento

### Tipos de Teste
1. **Testes Positivos**: Verificam comportamento esperado
2. **Testes Negativos**: Verificam tratamento de casos de erro
3. **Testes de Limite**: Verificam limites e restrições
4. **Testes de Performance**: Verificam tempo de execução
5. **Testes de Integração**: Verificam interação entre componentes

### Assertions Utilizadas
- `assertEquals()`: Verifica igualdade de valores
- `assertTrue()`/`assertFalse()`: Verifica condições booleanas
- `assertNotNull()`: Verifica se objeto não é nulo
- `assertDoesNotThrow()`: Verifica se método não lança exceção
- `assertTrue(duration < threshold)`: Verifica performance

## Melhorias Futuras

1. **Implementação das funcionalidades TODO**
   - Sistema de login funcional
   - Efetivação real de matrículas
   - Cancelamento real de matrículas
   - Encerramento de período com validações

2. **Testes Adicionais**
   - Testes de exceções
   - Testes de validação de dados
   - Testes de persistência (quando implementado)
   - Testes de interface de usuário (quando implementado)

3. **Melhorias nos Testes**
   - Uso de Mockito para mocks mais sofisticados
   - Testes parametrizados
   - Testes de propriedades (Property-based testing)
   - Cobertura de código mais abrangente

## Relatórios de Teste

Após executar os testes, os relatórios são gerados em:
- `target/surefire-reports/`: Relatórios detalhados de execução
- `target/site/jacoco/`: Relatórios de cobertura (quando configurado)

## Contribuição

Para adicionar novos testes:
1. Crie uma nova classe de teste seguindo o padrão `*Test.java`
2. Use anotações JUnit 5 apropriadas
3. Adicione `@DisplayName` descritivo
4. Documente casos de teste especiais
5. Execute os testes para garantir que passam
