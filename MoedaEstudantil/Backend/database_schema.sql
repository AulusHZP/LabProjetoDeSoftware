-- Database schema for Moeda Estudantil System - Company Module Only
-- PostgreSQL Database

-- Create database (run this separately)
-- CREATE DATABASE moeda_estudantil;

-- Create companies table
CREATE TABLE companies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    company_name VARCHAR(255) NOT NULL,
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create advantages table
CREATE TABLE advantages (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id UUID REFERENCES companies(id) ON DELETE CASCADE NOT NULL,
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

-- Create redemptions table
CREATE TABLE redemptions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    advantage_id UUID REFERENCES advantages(id) ON DELETE CASCADE NOT NULL,
    coupon_code VARCHAR(8) NOT NULL UNIQUE,
    student_email VARCHAR(255) NOT NULL,
    student_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX idx_companies_email ON companies(email);
CREATE INDEX idx_companies_cnpj ON companies(cnpj);
CREATE INDEX idx_advantages_company ON advantages(company_id);
CREATE INDEX idx_advantages_active ON advantages(is_active);
CREATE INDEX idx_redemptions_advantage ON redemptions(advantage_id);
CREATE INDEX idx_redemptions_coupon ON redemptions(coupon_code);

-- Function to generate coupon code
CREATE OR REPLACE FUNCTION generate_coupon_code()
RETURNS TEXT AS $$
BEGIN
    RETURN UPPER(SUBSTRING(MD5(RANDOM()::TEXT) FROM 1 FOR 8));
END;
$$ LANGUAGE plpgsql;

-- Function to increment redemption counter
CREATE OR REPLACE FUNCTION increment_redemption_counter()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE advantages
    SET current_redemptions = current_redemptions + 1
    WHERE id = NEW.advantage_id;
    RETURN NEW;
END;
$$;

-- Trigger to automatically increment counter on redemption
CREATE TRIGGER on_redemption_created
    AFTER INSERT ON redemptions
    FOR EACH ROW
    EXECUTE FUNCTION increment_redemption_counter();

-- Sample data for testing
INSERT INTO companies (company_name, cnpj, email, password) VALUES
    ('Tech Solutions Ltda', '12.345.678/0001-90', 'contato@techsolutions.com', 'password123'),
    ('BookStore Digital', '98.765.432/0001-10', 'contato@bookstore.com', 'password123'),
    ('Café & Cia', '11.222.333/0001-44', 'contato@cafeecia.com', 'password123');

-- Insert sample advantages
INSERT INTO advantages (company_id, title, description, coin_cost, is_active, max_redemptions) VALUES
    ((SELECT id FROM companies WHERE company_name = 'Tech Solutions Ltda'), 
     'Desconto 20% em Livros', 
     'Desconto de 20% em qualquer livro da nossa loja', 
     50, true, 10),
    ((SELECT id FROM companies WHERE company_name = 'BookStore Digital'), 
     'Café Grátis', 
     'Café expresso grátis em qualquer unidade', 
     30, true, 5),
    ((SELECT id FROM companies WHERE company_name = 'Café & Cia'), 
     'Desconto 15% em Tecnologia', 
     'Desconto de 15% em produtos de tecnologia', 
     100, true, 3);