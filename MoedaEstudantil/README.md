# 💰 Sistema de Moeda Estudantil (Release 1)
### Lab03S01 – Engenharia de Software | Laboratório de Desenvolvimento de Software  
**Professor:** João Paulo Carneiro Aramuni  
**Período:** 4º | **Valor:** 20 pontos  
**Entrega:** conforme cronograma no [GitHub do professor](https://github.com/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software/tree/main/CRONOGRAMA)

---

## 🧩 Descrição do Projeto

O **Sistema de Moeda Estudantil** tem como objetivo **estimular o reconhecimento do mérito acadêmico** por meio de uma **moeda virtual**.  
Essa moeda é **distribuída pelos professores aos alunos** e **trocada por produtos, serviços ou descontos** em **empresas parceiras** cadastradas.

O projeto segue o processo incremental proposto no **Laboratório 03**:

- **Lab03S01:** Modelagem do sistema (UML)
- **Lab03S02:** Modelo ER, DAO/ORM e CRUDs iniciais
- **Lab03S03:** CRUDs finais e apresentação da arquitetura

---

## 🧠 Requisitos Principais

- **Cadastro de Aluno:** nome, e-mail, CPF, RG, endereço, instituição e curso  
- **Cadastro de Professor:** nome, CPF, departamento e vínculo institucional  
- **Cadastro de Empresa Parceira:** nome, contato, e vantagens ofertadas  
- **Envio de Moedas:** professores podem enviar moedas aos alunos com uma mensagem obrigatória  
- **Notificação Automática:** alunos recebem e-mails ao receber moedas  
- **Consulta de Extrato:** professores e alunos visualizam transações e saldos  
- **Resgate de Vantagens:** alunos trocam moedas por produtos/descontos  
- **Geração de Cupom:** código enviado por e-mail ao aluno e à empresa  
- **Autenticação:** todos os usuários (aluno, professor, empresa) possuem login e senha  
- **Arquitetura:** modelo **MVC**, com integração entre camadas de controle, serviço e persistência  

---

## 🧮 Diagramas UML (Lab03S01)

### 🔹 Diagrama de Casos de Uso
Representa as interações entre os atores (Aluno, Professor, Empresa) e o sistema.

Arquivo: `usecase-moeda-estudantil.puml`

### 🔹 Histórias do Usuário (Mindmap)
Define as histórias sob a perspectiva de cada ator, documentando necessidades e benefícios.

Arquivo: `userstories-moeda-estudantil.puml`

### 🔹 Diagrama de Classes
Modela as entidades centrais do sistema e seus relacionamentos.

Arquivo: `classes-moeda-estudantil.puml`

### 🔹 Diagrama de Componentes
Descreve a arquitetura MVC e as integrações com serviços externos (e-mail e autenticação).

Arquivo: `componentes-moeda-estudantil.puml`

---

## 🧱 Estrutura do Projeto

