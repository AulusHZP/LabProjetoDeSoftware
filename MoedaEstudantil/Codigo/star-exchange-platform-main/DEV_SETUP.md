# Configuração de Desenvolvimento

## Opção 1: Com Docker (Recomendado para desenvolvimento completo)

### Pré-requisitos
- Docker Desktop instalado e rodando
- Node.js & npm

### Passos
```bash
# 1. Instalar dependências
npm install

# 2. Configurar variáveis de ambiente
cp env.example .env

# 3. Iniciar Supabase local
npx supabase start

# 4. Copiar as credenciais do Supabase para o .env
npx supabase status

# 5. Rodar frontend e backend juntos
npm run dev:full
```

## Opção 2: Sem Docker (Apenas Frontend)

### Pré-requisitos
- Node.js & npm
- Conta no Supabase (supabase.com)

### Passos
```bash
# 1. Instalar dependências
npm install

# 2. Configurar variáveis de ambiente
cp env.example .env

# 3. Editar .env com suas credenciais do Supabase
# VITE_SUPABASE_URL=https://seu-projeto.supabase.co
# VITE_SUPABASE_PUBLISHABLE_KEY=sua_chave_publica

# 4. Rodar apenas o frontend
npm run dev
```

## Opção 3: Desenvolvimento Híbrido

Se você tem um projeto Supabase na nuvem:

```bash
# 1. Instalar dependências
npm install

# 2. Configurar .env com suas credenciais de produção
# VITE_SUPABASE_URL=https://seu-projeto.supabase.co
# VITE_SUPABASE_PUBLISHABLE_KEY=sua_chave_publica

# 3. Rodar frontend
npm run dev
```

## Comandos Úteis

```bash
# Verificar status do Supabase
npm run supabase:status

# Parar Supabase
npm run supabase:stop

# Resetar banco de dados
npx supabase db reset

# Aplicar migrações
npx supabase db push
```
