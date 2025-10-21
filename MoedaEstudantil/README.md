# 🪙 Sistema de Moeda Estudantil - Spring Boot Backend

## Visão Geral

Sistema completo de moedas virtuais para reconhecimento estudantil desenvolvido com **Spring Boot** e **React**. Professores podem distribuir moedas para alunos por bom desempenho, e os alunos podem trocar essas moedas por vantagens oferecidas por empresas parceiras.

## 🎯 Funcionalidades

### Para Alunos
- **Dashboard personalizado** com saldo de moedas
- **Catálogo de vantagens** oferecidas por empresas parceiras
- **Sistema de resgate** com cupons únicos
- **Histórico completo** de transações e resgates
- **Interface intuitiva** para navegação

### Para Professores
- **Distribuição de moedas** para alunos por mérito
- **Sistema de reconhecimento** com motivos personalizados
- **Controle de saldo** próprio de moedas
- **Histórico de envios** e acompanhamento

### Para Empresas
- **Criação de vantagens** personalizadas
- **Gestão de ofertas** com custos em moedas
- **Controle de estoque** e disponibilidade
- **Acompanhamento de resgates** realizados

## 🛠️ Tecnologias Utilizadas

### Backend
- **Spring Boot 3.2.0** - Framework Java
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **PostgreSQL** - Banco de dados
- **JWT** - Tokens de autenticação
- **Spring Mail** - Envio de emails
- **Maven** - Gerenciamento de dependências

### Frontend
- **React 18** + TypeScript + Vite
- **shadcn/ui** + Tailwind CSS + Lucide Icons
- **React Router DOM** - Roteamento
- **React Hook Form** + Zod - Formulários e validação
- **Sonner** - Notificações

## 🚀 Como Executar

### Pré-requisitos

- **Java 17+** instalado
- **Maven** instalado
- **PostgreSQL** instalado e rodando
- **Node.js 18+** instalado

### 1. Configurar Banco de Dados

```sql
-- Criar banco de dados
CREATE DATABASE moeda_estudantil;

-- Executar o schema
\i Backend/database_schema.sql
```

### 2. Executar Backend

```bash
# Navegar para o diretório do backend
cd Backend

# Executar o Spring Boot
mvn spring-boot:run

# Ou usar o script
./run-backend.bat  # Windows
./run-backend.ps1  # PowerShell
```

O backend estará disponível em: `http://localhost:8080`

### 3. Executar Frontend

```bash
# Navegar para o diretório do frontend
cd Codigo/star-exchange-platform-main

# Instalar dependências
npm install

# Criar arquivo de ambiente
cp env.example .env

# Executar o frontend
npm run dev
```

O frontend estará disponível em: `http://localhost:5173`

## 📋 API Endpoints

### Autenticação
- `POST /api/auth/login` - Login
- `POST /api/auth/register` - Registro

### Instituições
- `GET /api/institutions` - Listar instituições
- `GET /api/institutions/{id}` - Buscar instituição

### Professor
- `POST /api/professor/send-coins` - Enviar moedas
- `GET /api/professor/transactions` - Histórico de envios
- `GET /api/professor/students/{institutionId}` - Listar alunos
- `GET /api/professor/students/search` - Buscar alunos

### Aluno
- `POST /api/student/redeem` - Resgatar vantagem
- `GET /api/student/transactions` - Histórico de recebimentos
- `GET /api/student/redemptions` - Histórico de resgates

### Empresa
- `POST /api/company/advantages` - Criar vantagem
- `GET /api/company/advantages` - Listar vantagens da empresa
- `PUT /api/company/advantages/{id}` - Atualizar vantagem
- `DELETE /api/company/advantages/{id}` - Deletar vantagem
- `GET /api/company/redemptions` - Histórico de resgates

### Vantagens (Público)
- `GET /api/advantages` - Listar vantagens disponíveis
- `GET /api/advantages/affordable/{maxCost}` - Vantagens acessíveis
- `GET /api/advantages/{id}` - Buscar vantagem

## 🗄️ Estrutura do Banco de Dados

### Tabelas Principais
- `institutions` - Instituições de ensino
- `profiles` - Perfis de usuários
- `students` - Dados dos alunos
- `professors` - Dados dos professores
- `companies` - Dados das empresas
- `advantages` - Vantagens oferecidas
- `transactions` - Transações de moedas
- `redemptions` - Resgates de vantagens

### Relacionamentos
- Um perfil pode ser aluno, professor ou empresa
- Alunos e professores pertencem a uma instituição
- Transações conectam professores e alunos
- Resgates conectam alunos e vantagens
- Vantagens pertencem a empresas

## 🔧 Configuração

### Variáveis de Ambiente

#### Backend (`application.yml`)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/moeda_estudantil
    username: postgres
    password: postgres

jwt:
  secret: mySecretKey123456789012345678901234567890
  expiration: 86400000

spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: ${MAIL_USERNAME:your-email@gmail.com}
    password: ${MAIL_PASSWORD:your-app-password}
```

#### Frontend (`.env`)
```env
VITE_API_URL=http://localhost:8080/api
```

## 📁 Estrutura do Projeto

```
MoedaEstudantil/
├── Backend/                     # Spring Boot Backend
│   ├── src/main/java/com/moedaestudantil/
│   │   ├── controller/          # Controllers REST
│   │   ├── service/            # Lógica de negócio
│   │   ├── repository/         # Repositórios JPA
│   │   ├── model/              # Entidades
│   │   ├── dto/                # DTOs
│   │   ├── config/             # Configurações
│   │   └── security/           # Segurança
│   ├── database_schema.sql     # Schema do banco
│   └── pom.xml                 # Dependências Maven
├── Codigo/star-exchange-platform-main/  # Frontend React
│   ├── src/
│   │   ├── components/          # Componentes React
│   │   ├── pages/              # Páginas
│   │   ├── services/           # Serviços API
│   │   └── hooks/              # Hooks customizados
│   └── package.json            # Dependências NPM
└── Documentacao/               # Diagramas UML
```

## 🔐 Segurança

- **JWT Tokens** para autenticação
- **BCrypt** para hash de senhas
- **CORS** configurado para frontend
- **Validação** de dados com Bean Validation
- **Autorização** baseada em roles

## 📧 Notificações

O sistema envia emails automáticos para:
- **Alunos**: Confirmação de resgate com código do cupom
- **Empresas**: Notificação de novo resgate realizado

## 🧪 Dados de Teste

O schema inclui dados de exemplo:
- **Instituições**: PUC Minas, UFMG, UEMG, UFV, UFOP
- **Usuário de teste**: 
  - Email: `aluno1@test.com`
  - Senha: `password123`
  - Tipo: Aluno

## 📝 Próximos Passos

- [ ] Implementar refresh automático de moedas para professores
- [ ] Adicionar sistema de notificações em tempo real
- [ ] Implementar upload de imagens para vantagens
- [ ] Adicionar relatórios e analytics
- [ ] Implementar testes automatizados
- [ ] Adicionar documentação Swagger/OpenAPI

## 👥 Autores

Desenvolvido como parte do projeto de Laboratório de Projeto de Software.

---

**Backend Spring Boot** substituindo completamente o Supabase com todas as funcionalidades mantidas e melhoradas!