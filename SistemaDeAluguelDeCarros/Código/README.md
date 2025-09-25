# RentCar Pro - Sistema de Aluguel de Carros

Sistema profissional de aluguel de automóveis desenvolvido com React, TypeScript e Tailwind CSS. Projetado para atender pessoas físicas, empresas e bancos com uma interface moderna e responsiva.

## 🚀 Funcionalidades

### Para Clientes
- **Cadastro e Login**: Sistema completo de autenticação
- **Explorar Veículos**: Catálogo com filtros avançados
- **Fazer Pedidos**: Solicitação de aluguel com diferentes tipos de contrato
- **Dashboard**: Acompanhamento de pedidos e contratos
- **Gestão de Pedidos**: Visualização de status e histórico

### Para Agentes (Empresas/Bancos)
- **Análise de Pedidos**: Aprovação ou rejeição de solicitações
- **Gestão de Frota**: Controle de veículos disponíveis
- **Dashboard Administrativo**: Métricas e relatórios
- **Gerenciamento de Clientes**: Controle de contratos ativos

## 🛠️ Tecnologias

- **React 18** - Framework frontend
- **TypeScript** - Tipagem estática
- **Tailwind CSS** - Estilização utilitária
- **Vite** - Build tool e dev server
- **React Router** - Navegação SPA
- **Zod** - Validação de formulários
- **Radix UI** - Componentes acessíveis
- **Lucide React** - Ícones modernos
- **React Query** - Gerenciamento de estado servidor

## 🎨 Design System

O sistema utiliza uma paleta de cores profissional focada em confiabilidade:

- **Primary**: Azul profissional (#2563eb)
- **Success**: Verde para aprovações
- **Warning**: Amarelo para pendências
- **Destructive**: Vermelho para rejeições

### Componentes Customizados

- Cards com hover effects elegantes
- Botões com gradientes e animações
- Formulários com validação visual
- Navegação responsiva
- Badges de status coloridos

## 🚗 Estrutura do Sistema

### Tipos de Usuário
1. **Cliente**: Pessoa física que aluga veículos
2. **Agente**: Empresa ou banco que aprova pedidos

### Tipos de Contrato
1. **Propriedade do Cliente**: Veículo fica em nome do cliente
2. **Empresa**: Contrato empresarial
3. **Banco/Financeira**: Contrato com instituição financeira

### Status de Pedidos
- **Pendente**: Aguardando análise
- **Aprovado**: Pedido aprovado, contrato ativo
- **Rejeitado**: Pedido negado

## 📱 Páginas e Rotas

- `/` - Homepage com hero section
- `/login` - Autenticação de usuário
- `/cadastro` - Registro de novos usuários
- `/dashboard` - Painel principal (cliente/agente)
- `/veiculos` - Catálogo de veículos
- `/pedidos` - Gestão de pedidos

## 🔧 Instalação e Uso

```bash
# Clonar o repositório
git clone <repository-url>

# Instalar dependências
npm install

# Executar em modo desenvolvimento
npm run dev

# Build para produção
npm run build
```

## 📋 Validações Implementadas

### Cadastro de Usuário
- Nome: 2-100 caracteres
- Email: Formato válido
- CPF: 11 dígitos
- RG: 7-15 caracteres
- Telefone: 10-15 dígitos
- Endereço: 10-200 caracteres
- Senha: Mínimo 6 caracteres
- Confirmação de senha

### Pedidos de Aluguel
- Seleção obrigatória de veículo
- Tipo de contrato obrigatório
- Datas de início e fim obrigatórias
- Observações opcionais (máximo 500 caracteres)

## 🎯 Funcionalidades Futuras

- [ ] Integração com APIs de pagamento
- [ ] Sistema de notificações por email
- [ ] Upload de documentos
- [ ] Integração com sistemas bancários
- [ ] Relatórios avançados
- [ ] Sistema de avaliações
- [ ] Chat interno
- [ ] Integração com GPS/Mapas
- [ ] Contratos digitais

## 🏗️ Arquitetura

O sistema foi projetado para integração com backend Java MVC via APIs RESTful:

```
Frontend (React/TS) ↔ REST API ↔ Backend (Java MVC) ↔ Database
```

### Estrutura de Pastas
```
src/
├── components/     # Componentes reutilizáveis
│   ├── layout/    # Header, Layout
│   └── ui/        # Componentes base (shadcn)
├── pages/         # Páginas da aplicação
├── hooks/         # Hooks customizados
├── lib/           # Utilitários
└── assets/        # Recursos estáticos
```

## 🎨 Guia de Estilo

- **Responsivo**: Mobile-first approach
- **Acessibilidade**: Componentes ARIA-compliant
- **Performance**: Lazy loading e otimizações
- **UX**: Feedback visual para todas as ações
- **Consistência**: Design system unificado

## 📄 Licença

Projeto desenvolvido para fins educacionais e comerciais.

## 🤝 Contribuição

Desenvolvido seguindo as melhores práticas de:
- Clean Code
- TypeScript strict mode
- Componentes reutilizáveis
- Design responsivo
- Validação robusta de formulários