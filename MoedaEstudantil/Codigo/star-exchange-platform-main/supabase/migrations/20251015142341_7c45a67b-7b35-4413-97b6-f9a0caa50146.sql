-- Add INSERT policy for professors table
CREATE POLICY "Professors can insert their own data" 
ON public.professors 
FOR INSERT 
WITH CHECK (auth.uid() = id);