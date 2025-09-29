# Sistema de Aluguel de Carros

Sistema completo de aluguel de carros desenvolvido com Spring Boot no backend e React/Vite no frontend.

## 🚀 Como Executar

### Backend (Spring Boot)

1. **Navegue até o diretório do backend:**
   ```bash
   cd SistemaDeAluguelDeCarros/Backend
   ```

2. **Execute o backend:**
   ```bash
   mvn spring-boot:run
   ```

3. **Acesse as APIs em:**
   - API: `http://localhost:8080/api/`
   - H2 Console: `http://localhost:8080/h2-console`

### Frontend (React)

1. **Navegue até o diretório do frontend:**
   ```bash
   cd SistemaDeAluguelDeCarros/Código
   ```

2. **Instale as dependências:**
   ```bash
   npm install
   ```

3. **Execute o frontend:**
   ```bash
   npm run dev
   ```

4. **Acesse a aplicação em:**
   - Frontend: `http://localhost:3000`
   - O frontend está configurado para fazer proxy automático para `http://localhost:8080`

## 🛠️ Funcionalidades Integradas

### ✅ Backend (Spring Boot)
- **Entidades JPA**: Usuario, Cliente, Agente, Pedido, Automovel, Contrato, etc.
- **Relacionamentos**: Implementados conforme diagrama UML
- **APIs REST**: Todas as operações CRUD
- **Validações**: Bean Validation
- **Banco H2**: Em memória para desenvolvimento
- **Segurança**: Spring Security básico

### ✅ Frontend (React)
- **Integração com Backend**: Toda comunicação via API REST
- **React Query**: Cache e gerenciamento de estado de dados
- **Formulários**: Validação com Zod
- **UI Components**: Shadcn/ui integrado
- **Responsive**: Design adaptável mobile/desktop

### 🔄 Conexões Implementadas

#### Veículos
- ✅ **Listagem**: Busca automóveis da API
- ✅ **Criação**: Formulário conectado com backend
- ✅ **Filtros**: Por marca, modelo, ano
- ✅ **Busca**: Por texto em marca/modelo

#### Pedidos
- ✅ **CRUD Completo**: Criar, listar, aprovar, rejeitar
- ✅ **Status**: PENDENTE, EM_ANALISE, APROVADO, REJEITADO
- ✅ **Filtragem**: Por cliente, agente, status
- ✅ **Validações**: Formulário validado antes envio

#### Dados de Conexão
- ✅ **Autenticação**: Mock (preparado para JWT)
- ✅ **Estado Global**: React Query para data management
- ✅ **Lint/Type Checking**: TypeScript + ESLint
- ✅ **Proxy DEV**: Webpack proxy para desenvolvimento

## 📋 APIs Disponíveis

### Veículos (`/api/automoveis`)
- `GET /` - Listar todos
- `GET /{id}` - Buscar por ID  
- `POST /` - Criar novo
- `PUT /{id}` - Atualizar
- `DELETE /{id}` - Remover

### Pedidos (`/api/pedidos`)
- `GET /` - Listar todos
- `GET /{id}` - Buscar por ID
- `POST /` - Criar pedido
- `PUT /{id}/aprovar` - Aprovar
- `PUT /{id}/rejeitar` - Rejeitar  
- `PUT /{id}/cancelar` - Cancelar

### Clientes (`/api/clientes`)
- `GET /` - Listar todos
- `POST /` - Criar cliente
- `PUT /{id}` - Atualizar cliente

## 🗂️ Estrutura do Projeto

```
SistemaDeAluguelDeCarros/
├── Backend/                    # Spring Boot
│   ├── src/main/java/...
│   ├── pom.xml
│   └── src/main/resources/application.yml
└── Código/                    # React Frontend  
    ├── src/
    │   ├── services/api.ts    # API Services
    │   ├── pages/Vehicles.tsx
    │   ├── pages/Orders.tsx
    │   └── components/
    ├── package.json
    └── vite.config.ts         # Proxy configurado
```

## 🔧 Variáveis de Ambiente

Para customizar URLs, crie um arquivo `.env` no frontend:
```env
VITE_API_URL=http://localhost:8080/api
```

## 📝 Notes de Desenvolvimento

- **Backend executará na porta 8080**
- **Frontend executará na porta 3000** 
- **Todas as requisições são auto-proxy configuradas**
- **Banco H2 limpa dados ao reiniciar (modo desenvolvimento)**
- **CORS habilitado para desenvolvimento**

O sistema está **totalmente conectado** entre frontend e backend! 🎉
