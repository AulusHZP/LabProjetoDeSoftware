# CRUD de Alunos - API Documentation

## Endpoints Disponíveis

### 1. Registrar Aluno
**POST** `/api/students/register`

Registra um novo aluno no sistema.

**Request Body:**
```json
{
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "cpf": "123.456.789-00",
  "rg": "12.345.678-9",
  "address": "Rua Exemplo, 123",
  "institutionId": 1,
  "course": "Engenharia de Software",
  "password": "senha123"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "cpf": "123.456.789-00",
  "rg": "12.345.678-9",
  "address": "Rua Exemplo, 123",
  "institutionId": 1,
  "institutionName": "Universidade XYZ",
  "course": "Engenharia de Software",
  "coinBalance": 0,
  "createdAt": "2025-10-20T10:30:00"
}
```

---

### 2. Login de Aluno
**POST** `/api/students/login`

Realiza o login de um aluno.

**Request Body:**
```json
{
  "email": "joao.silva@email.com",
  "password": "senha123"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "cpf": "123.456.789-00",
  "rg": "12.345.678-9",
  "address": "Rua Exemplo, 123",
  "institutionId": 1,
  "institutionName": "Universidade XYZ",
  "course": "Engenharia de Software",
  "coinBalance": 100,
  "createdAt": "2025-10-20T10:30:00"
}
```

---

### 3. Listar Todos os Alunos
**GET** `/api/students`

Retorna a lista de todos os alunos cadastrados.

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "João Silva",
    "email": "joao.silva@email.com",
    "cpf": "123.456.789-00",
    "rg": "12.345.678-9",
    "address": "Rua Exemplo, 123",
    "institutionId": 1,
    "institutionName": "Universidade XYZ",
    "course": "Engenharia de Software",
    "coinBalance": 100,
    "createdAt": "2025-10-20T10:30:00"
  }
]
```

---

### 4. Buscar Aluno por ID
**GET** `/api/students/{id}`

Retorna os dados de um aluno específico pelo ID.

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "cpf": "123.456.789-00",
  "rg": "12.345.678-9",
  "address": "Rua Exemplo, 123",
  "institutionId": 1,
  "institutionName": "Universidade XYZ",
  "course": "Engenharia de Software",
  "coinBalance": 100,
  "createdAt": "2025-10-20T10:30:00"
}
```

---

### 5. Buscar Aluno por Email
**GET** `/api/students/email/{email}`

Retorna os dados de um aluno específico pelo email.

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "cpf": "123.456.789-00",
  "rg": "12.345.678-9",
  "address": "Rua Exemplo, 123",
  "institutionId": 1,
  "institutionName": "Universidade XYZ",
  "course": "Engenharia de Software",
  "coinBalance": 100,
  "createdAt": "2025-10-20T10:30:00"
}
```

---

### 6. Atualizar Aluno
**PUT** `/api/students/{id}`

Atualiza os dados de um aluno. Todos os campos são opcionais.

**Request Body:**
```json
{
  "name": "João Pedro Silva",
  "email": "joao.pedro@email.com",
  "address": "Rua Nova, 456",
  "course": "Ciência da Computação",
  "password": "novaSenha123"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "João Pedro Silva",
  "email": "joao.pedro@email.com",
  "cpf": "123.456.789-00",
  "rg": "12.345.678-9",
  "address": "Rua Nova, 456",
  "institutionId": 1,
  "institutionName": "Universidade XYZ",
  "course": "Ciência da Computação",
  "coinBalance": 100,
  "createdAt": "2025-10-20T10:30:00"
}
```

---

### 7. Excluir Aluno
**DELETE** `/api/students/{id}`

Exclui um aluno do sistema.

**Response (200 OK):**
```json
{
  "message": "Aluno excluído com sucesso"
}
```

---

### 8. Atualizar Saldo de Moedas
**PATCH** `/api/students/{id}/coins?amount={valor}`

Atualiza o saldo de moedas do aluno. Valores positivos adicionam moedas, valores negativos subtraem.

**Exemplo:** `PATCH /api/students/1/coins?amount=50`

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "cpf": "123.456.789-00",
  "rg": "12.345.678-9",
  "address": "Rua Exemplo, 123",
  "institutionId": 1,
  "institutionName": "Universidade XYZ",
  "course": "Engenharia de Software",
  "coinBalance": 150,
  "createdAt": "2025-10-20T10:30:00"
}
```

---

## Códigos de Status HTTP

- **200 OK**: Requisição bem-sucedida
- **201 Created**: Aluno criado com sucesso
- **400 Bad Request**: Dados inválidos ou erro de validação
- **401 Unauthorized**: Credenciais inválidas (login)
- **404 Not Found**: Aluno não encontrado

---

## Validações

### Registro de Aluno:
- **name**: Obrigatório, não pode ser vazio
- **email**: Obrigatório, deve ser um email válido e único
- **cpf**: Obrigatório e único
- **rg**: Obrigatório
- **address**: Obrigatório
- **institutionId**: Obrigatório, deve existir na tabela de instituições
- **course**: Obrigatório
- **password**: Obrigatório

### Login:
- **email**: Obrigatório, deve ser um email válido
- **password**: Obrigatório

---

## Observações

1. **Senhas**: Atualmente as senhas são armazenadas em texto plano. Em produção, é necessário implementar hash de senha (BCrypt).

2. **Saldo de Moedas**: 
   - Inicia em 0 para novos alunos
   - Não pode ficar negativo
   - Pode ser atualizado via endpoint específico

3. **Instituição**: O aluno deve estar associado a uma instituição existente no momento do registro.

4. **CORS**: Habilitado para todos os origins (`*`). Em produção, configurar origins específicos.

---

## Exemplos de Uso com cURL

### Registrar Aluno:
```bash
curl -X POST http://localhost:8080/api/students/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao.silva@email.com",
    "cpf": "123.456.789-00",
    "rg": "12.345.678-9",
    "address": "Rua Exemplo, 123",
    "institutionId": 1,
    "course": "Engenharia de Software",
    "password": "senha123"
  }'
```

### Login:
```bash
curl -X POST http://localhost:8080/api/students/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao.silva@email.com",
    "password": "senha123"
  }'
```

### Listar Todos:
```bash
curl -X GET http://localhost:8080/api/students
```

### Buscar por ID:
```bash
curl -X GET http://localhost:8080/api/students/1
```

### Atualizar:
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Pedro Silva",
    "address": "Rua Nova, 456"
  }'
```

### Adicionar Moedas:
```bash
curl -X PATCH "http://localhost:8080/api/students/1/coins?amount=50"
```

### Excluir:
```bash
curl -X DELETE http://localhost:8080/api/students/1
```
