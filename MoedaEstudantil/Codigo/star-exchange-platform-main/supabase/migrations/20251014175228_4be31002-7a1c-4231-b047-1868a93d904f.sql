-- Create enum for user types
CREATE TYPE user_type AS ENUM ('student', 'professor', 'company');

-- Create institutions table
CREATE TABLE public.institutions (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name TEXT NOT NULL,
  created_at TIMESTAMPTZ DEFAULT now()
);

-- Create profiles table linked to auth.users
CREATE TABLE public.profiles (
  id UUID PRIMARY KEY REFERENCES auth.users(id) ON DELETE CASCADE,
  user_type user_type NOT NULL,
  email TEXT NOT NULL,
  created_at TIMESTAMPTZ DEFAULT now()
);

-- Create students table
CREATE TABLE public.students (
  id UUID PRIMARY KEY REFERENCES public.profiles(id) ON DELETE CASCADE,
  name TEXT NOT NULL,
  cpf TEXT NOT NULL UNIQUE,
  rg TEXT NOT NULL,
  address TEXT NOT NULL,
  institution_id UUID REFERENCES public.institutions(id) NOT NULL,
  course TEXT NOT NULL,
  coin_balance INTEGER DEFAULT 0,
  created_at TIMESTAMPTZ DEFAULT now()
);

-- Create professors table
CREATE TABLE public.professors (
  id UUID PRIMARY KEY REFERENCES public.profiles(id) ON DELETE CASCADE,
  name TEXT NOT NULL,
  cpf TEXT NOT NULL UNIQUE,
  department TEXT NOT NULL,
  institution_id UUID REFERENCES public.institutions(id) NOT NULL,
  coin_balance INTEGER DEFAULT 1000,
  last_coin_refresh TIMESTAMPTZ DEFAULT now(),
  created_at TIMESTAMPTZ DEFAULT now()
);

-- Create companies table
CREATE TABLE public.companies (
  id UUID PRIMARY KEY REFERENCES public.profiles(id) ON DELETE CASCADE,
  company_name TEXT NOT NULL,
  cnpj TEXT NOT NULL UNIQUE,
  created_at TIMESTAMPTZ DEFAULT now()
);

-- Create advantages table
CREATE TABLE public.advantages (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  company_id UUID REFERENCES public.companies(id) ON DELETE CASCADE NOT NULL,
  title TEXT NOT NULL,
  description TEXT NOT NULL,
  photo_url TEXT,
  coin_cost INTEGER NOT NULL,
  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMPTZ DEFAULT now()
);

-- Create transactions table
CREATE TABLE public.transactions (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  professor_id UUID REFERENCES public.professors(id) ON DELETE CASCADE NOT NULL,
  student_id UUID REFERENCES public.students(id) ON DELETE CASCADE NOT NULL,
  amount INTEGER NOT NULL,
  reason TEXT NOT NULL,
  created_at TIMESTAMPTZ DEFAULT now()
);

-- Create redemptions table
CREATE TABLE public.redemptions (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  student_id UUID REFERENCES public.students(id) ON DELETE CASCADE NOT NULL,
  advantage_id UUID REFERENCES public.advantages(id) ON DELETE CASCADE NOT NULL,
  coupon_code TEXT NOT NULL UNIQUE,
  created_at TIMESTAMPTZ DEFAULT now()
);

-- Enable Row Level Security
ALTER TABLE public.institutions ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.profiles ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.students ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.professors ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.companies ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.advantages ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.transactions ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.redemptions ENABLE ROW LEVEL SECURITY;

-- RLS Policies for institutions (public read)
CREATE POLICY "Institutions are viewable by everyone"
  ON public.institutions FOR SELECT
  USING (true);

-- RLS Policies for profiles
CREATE POLICY "Users can view their own profile"
  ON public.profiles FOR SELECT
  USING (auth.uid() = id);

CREATE POLICY "Users can insert their own profile"
  ON public.profiles FOR INSERT
  WITH CHECK (auth.uid() = id);

CREATE POLICY "Users can update their own profile"
  ON public.profiles FOR UPDATE
  USING (auth.uid() = id);

-- RLS Policies for students
CREATE POLICY "Students can view their own data"
  ON public.students FOR SELECT
  USING (auth.uid() = id);

CREATE POLICY "Students can insert their own data"
  ON public.students FOR INSERT
  WITH CHECK (auth.uid() = id);

CREATE POLICY "Students can update their own data"
  ON public.students FOR UPDATE
  USING (auth.uid() = id);

CREATE POLICY "Professors can view students"
  ON public.students FOR SELECT
  USING (EXISTS (
    SELECT 1 FROM public.professors WHERE id = auth.uid()
  ));

-- RLS Policies for professors
CREATE POLICY "Professors can view their own data"
  ON public.professors FOR SELECT
  USING (auth.uid() = id);

CREATE POLICY "Professors can update their own data"
  ON public.professors FOR UPDATE
  USING (auth.uid() = id);

-- RLS Policies for companies
CREATE POLICY "Companies can view their own data"
  ON public.companies FOR SELECT
  USING (auth.uid() = id);

CREATE POLICY "Companies can insert their own data"
  ON public.companies FOR INSERT
  WITH CHECK (auth.uid() = id);

CREATE POLICY "Companies can update their own data"
  ON public.companies FOR UPDATE
  USING (auth.uid() = id);

CREATE POLICY "Everyone can view companies"
  ON public.companies FOR SELECT
  USING (true);

-- RLS Policies for advantages
CREATE POLICY "Companies can manage their own advantages"
  ON public.advantages FOR ALL
  USING (auth.uid() = company_id);

CREATE POLICY "Students can view active advantages"
  ON public.advantages FOR SELECT
  USING (is_active = true);

-- RLS Policies for transactions
CREATE POLICY "Professors can view their sent transactions"
  ON public.transactions FOR SELECT
  USING (auth.uid() = professor_id);

CREATE POLICY "Professors can create transactions"
  ON public.transactions FOR INSERT
  WITH CHECK (auth.uid() = professor_id);

CREATE POLICY "Students can view their received transactions"
  ON public.transactions FOR SELECT
  USING (auth.uid() = student_id);

-- RLS Policies for redemptions
CREATE POLICY "Students can view their own redemptions"
  ON public.redemptions FOR SELECT
  USING (auth.uid() = student_id);

CREATE POLICY "Students can create redemptions"
  ON public.redemptions FOR INSERT
  WITH CHECK (auth.uid() = student_id);

CREATE POLICY "Companies can view redemptions of their advantages"
  ON public.redemptions FOR SELECT
  USING (EXISTS (
    SELECT 1 FROM public.advantages a
    INNER JOIN public.companies c ON a.company_id = c.id
    WHERE a.id = redemptions.advantage_id AND c.id = auth.uid()
  ));

-- Function to generate coupon code
CREATE OR REPLACE FUNCTION generate_coupon_code()
RETURNS TEXT AS $$
BEGIN
  RETURN UPPER(SUBSTRING(MD5(RANDOM()::TEXT) FROM 1 FOR 8));
END;
$$ LANGUAGE plpgsql;

-- Insert sample institutions
INSERT INTO public.institutions (name) VALUES
  ('PUC Minas'),
  ('UFMG'),
  ('UEMG');

-- Function to create profile automatically after user signup
CREATE OR REPLACE FUNCTION public.handle_new_user()
RETURNS TRIGGER AS $$
BEGIN
  INSERT INTO public.profiles (id, user_type, email)
  VALUES (
    NEW.id,
    (NEW.raw_user_meta_data->>'user_type')::user_type,
    NEW.email
  );
  RETURN NEW;
END;
$$ LANGUAGE plpgsql SECURITY DEFINER SET search_path = public;

-- Trigger to create profile on user creation
CREATE TRIGGER on_auth_user_created
  AFTER INSERT ON auth.users
  FOR EACH ROW EXECUTE FUNCTION public.handle_new_user();