# Sistema de Moeda Estudantil

Este projeto é um sistema desenvolvido para incentivar o reconhecimento do mérito estudantil por meio de uma **moeda virtual**. O sistema contempla funcionalidades para **alunos, professores e empresas parceiras**, incluindo modelagem UML, autenticação, controle de saldo e comunicação via e-mail.

## Sumário
- [Visão Geral](#visão-geral)
- [Funcionalidades](#funcionalidades)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Como Executar](#como-executar)
- [Banco de Dados](#banco-de-dados)
- [Documentação](#documentação)
- [Autores](#autores)

---

## Visão Geral
O **Sistema de Moeda Estudantil** visa criar um ambiente digital onde professores podem **premiar alunos** com moedas virtuais por bom comportamento e desempenho acadêmico. Os alunos podem **trocar essas moedas** por vantagens oferecidas por empresas parceiras, como **descontos em produtos ou serviços**.

O projeto está dividido em **três sprints principais (Lab03S01, S02 e S03)** e segue uma **arquitetura MVC**, com documentação UML e integração entre frontend e backend.

---

## Funcionalidades
- Cadastro e autenticação de usuários (aluno, professor e empresa parceira)  
- Envio de moedas de professores para alunos com mensagens personalizadas  
- Consulta de extrato de transações (envios, recebimentos e resgates)  
- Cadastro de vantagens por empresas parceiras (com descrição, foto e custo em moedas)  
- Resgate de vantagens por alunos, com geração automática de **cupom e código**  
- Notificação por e-mail para aluno e empresa após o resgate  
- Crédito automático de **1.000 moedas por semestre** para cada professor  
- Arquitetura **MVC** e integração entre frontend e backend  

---

## Estrutura do Projeto
