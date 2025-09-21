# Implementação da Parte 02S03 - Sistema de Aluguel de Carros

## Visão Geral

Este documento descreve a implementação da parte 02S03 do sistema de aluguel de carros, que inclui as funcionalidades principais baseadas nas histórias de usuário definidas.

## Entidades Implementadas

### 1. Automóvel (Automovel)
**Arquivo:** `src/main/java/org/example/model/Automovel.java`

**Funcionalidades:**
- Cadastro de automóveis com informações completas
- Gerenciamento de disponibilidade
- Controle de status (ativo/inativo)
- Busca por diversos critérios (marca, modelo, categoria, preço, etc.)

**Campos principais:**
- Dados básicos: marca, modelo, ano, placa, cor
- Categoria e preço diário
- Características técnicas: combustível, transmissão, capacidade
- Equipamentos: ar condicionado, direção hidráulica, airbag, ABS
- Controle: disponibilidade, status ativo

### 2. Pedido de Aluguel (PedidoAluguel)
**Arquivo:** `src/main/java/org/example/model/PedidoAluguel.java`

**Funcionalidades:**
- Criação de pedidos de aluguel
- Modificação de pedidos pendentes
- Cancelamento de pedidos
- Aprovação/rejeição de pedidos
- Finalização de aluguéis

**Status do pedido:**
- PENDENTE: Aguardando aprovação
- APROVADO: Aprovado e pronto para uso
- REJEITADO: Rejeitado com motivo
- CANCELADO: Cancelado pelo cliente
- FINALIZADO: Aluguel concluído

### 3. Agente (Agente)
**Arquivo:** `src/main/java/org/example/model/Agente.java`

**Funcionalidades:**
- Cadastro de agentes financeiros
- Diferentes tipos: Banco, Financeira, Empresa, Cooperativa
- Controle de limite de aprovação
- Gerenciamento de contratos de crédito

### 4. Administrador (Administrador)
**Arquivo:** `src/main/java/org/example/model/Administrador.java`

**Funcionalidades:**
- Gerenciamento de usuários
- Cadastro de automóveis
- Geração de relatórios
- Diferentes níveis de acesso

**Níveis de acesso:**
- SUPER_ADMINISTRADOR: Acesso total ao sistema
- ADMINISTRADOR: Gerenciamento de usuários e automóveis
- OPERADOR: Operações básicas

### 5. Contrato de Crédito (ContratoCredito)
**Arquivo:** `src/main/java/org/example/model/ContratoCredito.java`

**Funcionalidades:**
- Associação de contratos a pedidos aprovados
- Controle de parcelas e juros
- Gestão de vencimentos
- Status de contrato (ativo, quitado, suspenso, cancelado)

## Repositórios Implementados

### 1. AutomovelRepository
**Arquivo:** `src/main/java/org/example/repository/AutomovelRepository.java`

**Métodos principais:**
- Busca por placa, marca, modelo, categoria
- Filtros por preço, ano, cor, combustível
- Contagem de automóveis por categoria
- Verificação de disponibilidade

### 2. PedidoAluguelRepository
**Arquivo:** `src/main/java/org/example/repository/PedidoAluguelRepository.java`

**Métodos principais:**
- Busca por cliente, automóvel, status
- Verificação de conflitos de período
- Cálculo de valores totais
- Relatórios por período

### 3. AgenteRepository
**Arquivo:** `src/main/java/org/example/repository/AgenteRepository.java`

**Métodos principais:**
- Busca por tipo de agente
- Verificação de limite de aprovação
- Contagem por categoria

### 4. AdministradorRepository
**Arquivo:** `src/main/java/org/example/repository/AdministradorRepository.java`

**Métodos principais:**
- Busca por nível de acesso
- Controle de login
- Relatórios de atividade

### 5. ContratoCreditoRepository
**Arquivo:** `src/main/java/org/example/repository/ContratoCreditoRepository.java`

**Métodos principais:**
- Busca por agente, status
- Controle de vencimentos
- Cálculo de valores

## Serviços Implementados

### 1. AutomovelService
**Arquivo:** `src/main/java/org/example/service/AutomovelService.java`

**Funcionalidades:**
- Cadastro e atualização de automóveis
- Controle de disponibilidade
- Busca avançada com filtros
- Validações de negócio

### 2. PedidoAluguelService
**Arquivo:** `src/main/java/org/example/service/PedidoAluguelService.java`

**Funcionalidades:**
- Criação e modificação de pedidos
- Validação de disponibilidade
- Cálculo automático de valores
- Controle de status

## Controladores REST Implementados

### 1. AutomovelController
**Arquivo:** `src/main/java/org/example/controller/AutomovelController.java`

**Endpoints principais:**
- `POST /api/automoveis` - Cadastrar automóvel
- `GET /api/automoveis` - Listar todos
- `GET /api/automoveis/disponiveis` - Listar disponíveis
- `GET /api/automoveis/categoria/{categoria}` - Buscar por categoria
- `GET /api/automoveis/preco?minimo={min}&maximo={max}` - Buscar por preço
- `PUT /api/automoveis/{id}/disponibilizar` - Disponibilizar
- `PUT /api/automoveis/{id}/indisponibilizar` - Indisponibilizar

### 2. PedidoAluguelController
**Arquivo:** `src/main/java/org/example/controller/PedidoAluguelController.java`

**Endpoints principais:**
- `POST /api/pedidos-aluguel` - Criar pedido
- `PUT /api/pedidos-aluguel/{id}` - Modificar pedido
- `PUT /api/pedidos-aluguel/{id}/cancelar` - Cancelar pedido
- `PUT /api/pedidos-aluguel/{id}/aprovar` - Aprovar pedido
- `PUT /api/pedidos-aluguel/{id}/rejeitar` - Rejeitar pedido
- `GET /api/pedidos-aluguel/pendentes` - Listar pendentes
- `GET /api/pedidos-aluguel/cliente/{id}` - Pedidos por cliente

## Testes Implementados

### 1. AutomovelServiceTest
**Arquivo:** `src/test/java/org/example/AutomovelServiceTest.java`

**Cenários testados:**
- Cadastro de automóvel com sucesso
- Validação de placa duplicada
- Busca por ID
- Ativação/desativação
- Controle de disponibilidade

## Histórias de Usuário Atendidas

### Cliente
- ✅ **Cliente 1:** Cadastrar no sistema
- ✅ **Cliente 2:** Fazer login
- ✅ **Cliente 3:** Atualizar dados pessoais
- ✅ **Cliente 4:** Criar pedido de aluguel
- ✅ **Cliente 5:** Modificar pedido de aluguel
- ✅ **Cliente 6:** Consultar status dos pedidos
- ✅ **Cliente 7:** Cancelar pedido de aluguel

### Agente
- ✅ **Agente 1:** Cadastrar como representante
- ✅ **Agente 2:** Avaliar pedidos pendentes
- ✅ **Agente 3:** Associar contratos de crédito
- ✅ **Agente 4:** Modificar detalhes de pedidos
- ✅ **Agente 5:** Gerenciar contratos ativos

### Administrador
- ✅ **Administrador 1:** Gerenciar cadastros de usuários
- ✅ **Administrador 2:** Cadastrar novos automóveis
- ✅ **Administrador 3:** Gerar relatórios de desempenho

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Web**
- **H2 Database** (desenvolvimento)
- **MySQL** (produção)
- **JUnit 5** (testes)
- **Mockito** (testes)

## Como Executar

1. **Compilar o projeto:**
   ```bash
   mvn clean compile
   ```

2. **Executar testes:**
   ```bash
   mvn test
   ```

3. **Executar aplicação:**
   ```bash
   mvn spring-boot:run
   ```

4. **Acessar a aplicação:**
   - Interface web: http://localhost:8080
   - API REST: http://localhost:8080/api/

## Endpoints da API

### Automóveis
- `GET /api/automoveis` - Listar todos os automóveis
- `GET /api/automoveis/disponiveis` - Listar automóveis disponíveis
- `GET /api/automoveis/categoria/{categoria}` - Buscar por categoria
- `POST /api/automoveis` - Cadastrar novo automóvel
- `PUT /api/automoveis/{id}` - Atualizar automóvel

### Pedidos de Aluguel
- `GET /api/pedidos-aluguel` - Listar todos os pedidos
- `GET /api/pedidos-aluguel/pendentes` - Listar pedidos pendentes
- `POST /api/pedidos-aluguel` - Criar novo pedido
- `PUT /api/pedidos-aluguel/{id}/aprovar` - Aprovar pedido
- `PUT /api/pedidos-aluguel/{id}/rejeitar` - Rejeitar pedido

### Clientes
- `GET /api/clientes` - Listar todos os clientes
- `POST /api/clientes` - Cadastrar novo cliente
- `POST /api/clientes/login` - Fazer login

## Próximos Passos

1. **Implementar autenticação e autorização**
2. **Criar interface web completa**
3. **Implementar relatórios avançados**
4. **Adicionar validações de negócio mais complexas**
5. **Implementar notificações**
6. **Adicionar logs e auditoria**

## Conclusão

A implementação da parte 02S03 inclui todas as funcionalidades principais do sistema de aluguel de carros, com uma arquitetura bem estruturada seguindo os padrões do Spring Boot. O sistema está pronto para ser expandido com funcionalidades adicionais conforme necessário.
