-- Create function to increment redemption counter
CREATE OR REPLACE FUNCTION public.increment_redemption_counter()
RETURNS TRIGGER
LANGUAGE plpgsql
SECURITY DEFINER
SET search_path = public
AS $$
BEGIN
  UPDATE public.advantages
  SET current_redemptions = current_redemptions + 1
  WHERE id = NEW.advantage_id;
  RETURN NEW;
END;
$$;

-- Create trigger to automatically increment counter on redemption
CREATE TRIGGER on_redemption_created
AFTER INSERT ON public.redemptions
FOR EACH ROW
EXECUTE FUNCTION public.increment_redemption_counter();