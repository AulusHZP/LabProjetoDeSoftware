# 🪙 Plataforma de Moedas Estudantis

## Visão Geral

Uma plataforma web moderna que permite o gerenciamento de um sistema de moedas virtuais para reconhecimento estudantil. Professores podem distribuir moedas para alunos por bom desempenho, e os alunos podem trocar essas moedas por vantagens oferecidas por empresas parceiras.

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

- **Frontend**: React 18 + TypeScript + Vite
- **UI/UX**: shadcn/ui + Tailwind CSS + Lucide Icons
- **Backend**: Supabase (PostgreSQL + Auth + Real-time)
- **Gerenciamento de Estado**: React Hooks + Context API
- **Roteamento**: React Router DOM
- **Formulários**: React Hook Form + Zod
- **Notificações**: Sonner

## 🚀 Como Executar

### Pré-requisitos

- Node.js 18+ instalado - [instalar com nvm](https://github.com/nvm-sh/nvm#installing-and-updating)
- Supabase CLI instalado - [instalar Supabase CLI](https://supabase.com/docs/guides/cli/getting-started)

### Instalação

```bash
# 1. Clone o repositório
git clone <URL_DO_REPOSITORIO>

# 2. Navegue até o diretório do projeto
cd star-exchange-platform

# 3. Instale as dependências
npm install

# 4. Configure as variáveis de ambiente
cp env.example .env

# 5. Inicie o Supabase localmente (primeira vez)
npx supabase start

# 6. Obtenha as credenciais locais do Supabase
npx supabase status
```

### Desenvolvimento

#### Opção 1: Executar Frontend e Backend Juntos (Recomendado)

```bash
# Inicia tanto o frontend React quanto o backend Supabase
npm run dev:full
```

#### Opção 2: Executar Separadamente

```bash
# Terminal 1: Iniciar backend Supabase
npm run supabase:start

# Terminal 2: Iniciar frontend React
npm run dev
```

#### Comandos Úteis do Supabase

```bash
# Verificar status do Supabase
npm run supabase:status

# Parar Supabase
npm run supabase:stop

# Resetar banco de dados
npx supabase db reset
```

## 📋 Scripts Disponíveis

- `npm run dev` - Iniciar servidor de desenvolvimento (apenas frontend)
- `npm run dev:full` - Iniciar frontend e backend Supabase
- `npm run build` - Build para produção
- `npm run preview` - Preview do build de produção
- `npm run lint` - Executar ESLint
- `npm run supabase:start` - Iniciar Supabase local
- `npm run supabase:stop` - Parar Supabase
- `npm run supabase:status` - Verificar status do Supabase

## 📁 Estrutura do Projeto

```
src/
├── components/          # Componentes reutilizáveis da UI
│   └── ui/             # Componentes base do shadcn/ui
├── pages/              # Páginas da aplicação
│   ├── Auth.tsx        # Página de autenticação
│   ├── StudentDashboard.tsx    # Dashboard do aluno
│   ├── ProfessorDashboard.tsx  # Dashboard do professor
│   └── CompanyDashboard.tsx   # Dashboard da empresa
├── hooks/              # Hooks customizados do React
├── lib/                # Funções utilitárias
├── integrations/       # Integrações com serviços externos
│   └── supabase/       # Configuração do Supabase
└── utils/              # Utilitários diversos

supabase/               # Migrações e configuração do banco
├── migrations/         # Scripts de migração do banco
└── config.toml        # Configuração do Supabase
```

## 🔧 Variáveis de Ambiente

Copie `env.example` para `.env` e configure com suas credenciais do Supabase:

```env
VITE_SUPABASE_URL=http://localhost:54321
VITE_SUPABASE_PUBLISHABLE_KEY=sua_chave_anonima_aqui
```

## 🚀 Deploy

Para fazer o build do projeto para produção:

```bash
npm run build
```

Os arquivos compilados estarão no diretório `dist`, prontos para deploy em qualquer serviço de hospedagem estática.

## 🎨 Características da Interface

- **Design responsivo** que funciona em desktop e mobile
- **Tema moderno** com gradientes e sombras
- **Componentes acessíveis** seguindo padrões de acessibilidade
- **Feedback visual** com toasts e animações
- **Navegação intuitiva** com tabs e modais

## 🔐 Sistema de Autenticação

- **Autenticação segura** via Supabase Auth
- **Diferentes tipos de usuário**: Aluno, Professor, Empresa
- **Controle de acesso** baseado em roles
- **Sessões persistentes** com refresh automático
