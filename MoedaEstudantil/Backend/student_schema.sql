-- Script SQL para criar todas as tabelas do sistema de moeda estudantil
-- Execute este script no Railway

-- Apagar tabelas existentes (se existirem)
DROP TABLE IF EXISTS redemptions CASCADE;
DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS advantages CASCADE;
DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS professors CASCADE;
DROP TABLE IF EXISTS institutions CASCADE;
DROP TABLE IF EXISTS companies CASCADE;

-- Criar tabela de instituições
CREATE TABLE institutions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    address TEXT NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de empresas
CREATE TABLE companies (
    id BIGSERIAL PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de alunos
CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    rg VARCHAR(20) NOT NULL,
    address TEXT NOT NULL,
    institution_id BIGINT REFERENCES institutions(id) ON DELETE CASCADE NOT NULL,
    course VARCHAR(255) NOT NULL,
    coin_balance INTEGER DEFAULT 0,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de professores
CREATE TABLE professors (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    department VARCHAR(255) NOT NULL,
    institution_id BIGINT REFERENCES institutions(id) ON DELETE CASCADE NOT NULL,
    coin_balance INTEGER DEFAULT 1000,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de vantagens
CREATE TABLE advantages (
    id BIGSERIAL PRIMARY KEY,
    company_id BIGINT REFERENCES companies(id) ON DELETE CASCADE NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    photo_url TEXT,
    coin_cost INTEGER NOT NULL,
    is_active BOOLEAN DEFAULT true,
    max_redemptions INTEGER DEFAULT 1,
    current_redemptions INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT check_redemptions_limit CHECK (current_redemptions <= max_redemptions)
);

-- Criar tabela de transações
CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    professor_id BIGINT REFERENCES professors(id) ON DELETE CASCADE,
    student_id BIGINT REFERENCES students(id) ON DELETE CASCADE NOT NULL,
    amount INTEGER NOT NULL,
    reason TEXT NOT NULL,
    type VARCHAR(50) NOT NULL CHECK (type IN ('PROFESSOR_TO_STUDENT', 'STUDENT_REDEMPTION')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de resgates
CREATE TABLE redemptions (
    id BIGSERIAL PRIMARY KEY,
    advantage_id BIGINT REFERENCES advantages(id) ON DELETE CASCADE NOT NULL,
    student_id BIGINT REFERENCES students(id) ON DELETE CASCADE NOT NULL,
    coupon_code VARCHAR(8) NOT NULL UNIQUE,
    student_email VARCHAR(255) NOT NULL,
    student_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Inserir dados de exemplo

-- Instituições
INSERT INTO institutions (name, cnpj, address, phone, email) VALUES
    ('Universidade Federal de São Paulo', '12.345.678/0001-90', 'Rua Sena Madureira, 1500 - Vila Clementino, São Paulo - SP', '(11) 3385-4000', 'contato@unifesp.br'),
    ('Universidade de São Paulo', '98.765.432/0001-10', 'Av. Prof. Luciano Gualberto, 380 - Butantã, São Paulo - SP', '(11) 3091-4000', 'contato@usp.br'),
    ('Universidade Estadual de Campinas', '11.222.333/0001-44', 'Rua da Reitoria, 109 - Cidade Universitária, Campinas - SP', '(19) 3521-4000', 'contato@unicamp.br');

-- Empresas
INSERT INTO companies (company_name, cnpj, email, password) VALUES
    ('Tech Solutions Ltda', '12.345.678/0001-90', 'contato@techsolutions.com', 'password123'),
    ('BookStore Digital', '98.765.432/0001-10', 'contato@bookstore.com', 'password123'),
    ('Café & Cia', '11.222.333/0001-44', 'contato@cafeecia.com', 'password123');

-- Professores
INSERT INTO professors (name, cpf, department, institution_id, password) VALUES
    ('Dr. João Silva', '123.456.789-00', 'Ciência da Computação', 1, 'password123'),
    ('Dra. Maria Santos', '987.654.321-00', 'Engenharia', 1, 'password123'),
    ('Dr. Pedro Oliveira', '111.222.333-44', 'Matemática', 2, 'password123');

-- Alunos de exemplo
INSERT INTO students (name, email, cpf, rg, address, institution_id, course, password) VALUES
    ('Ana Costa', 'ana.costa@email.com', '111.111.111-11', '12.345.678-9', 'Rua A, 123 - São Paulo, SP', 1, 'Ciência da Computação', 'password123'),
    ('Bruno Lima', 'bruno.lima@email.com', '222.222.222-22', '23.456.789-0', 'Rua B, 456 - São Paulo, SP', 1, 'Engenharia de Software', 'password123'),
    ('Carlos Mendes', 'carlos.mendes@email.com', '333.333.333-33', '34.567.890-1', 'Rua C, 789 - Campinas, SP', 2, 'Matemática', 'password123');

-- Vantagens de exemplo
INSERT INTO advantages (company_id, title, description, photo_url, coin_cost, max_redemptions) VALUES
    (1, 'Vale Alimentação', 'Desconto de 20% em restaurantes parceiros', 'https://example.com/photo1.jpg', 150, 5),
    (2, 'Livro Técnico', 'Desconto de 30% em livros técnicos', 'https://example.com/photo2.jpg', 200, 3),
    (3, 'Café Especial', 'Café premium com desconto de 25%', 'https://example.com/photo3.jpg', 50, 10);

-- Transações de exemplo
INSERT INTO transactions (professor_id, student_id, amount, reason, type) VALUES
    (1, 1, 50, 'Excelente participação em aula', 'PROFESSOR_TO_STUDENT'),
    (1, 2, 75, 'Trabalho excepcional entregue', 'PROFESSOR_TO_STUDENT'),
    (2, 1, 100, 'Liderança em projeto de grupo', 'PROFESSOR_TO_STUDENT');

-- Atualizar saldo dos alunos baseado nas transações
UPDATE students SET coin_balance = (
    SELECT COALESCE(SUM(amount), 0) 
    FROM transactions 
    WHERE student_id = students.id AND type = 'PROFESSOR_TO_STUDENT'
) - (
    SELECT COALESCE(SUM(amount), 0) 
    FROM transactions 
    WHERE student_id = students.id AND type = 'STUDENT_REDEMPTION'
);

-- Atualizar saldo dos professores baseado nas transações
UPDATE professors SET coin_balance = 1000 - (
    SELECT COALESCE(SUM(amount), 0) 
    FROM transactions 
    WHERE professor_id = professors.id AND type = 'PROFESSOR_TO_STUDENT'
);
