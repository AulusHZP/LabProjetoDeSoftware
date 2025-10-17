-- Add redemption counter fields to advantages table
ALTER TABLE public.advantages 
ADD COLUMN max_redemptions integer DEFAULT 1,
ADD COLUMN current_redemptions integer DEFAULT 0;

-- Add check constraint to ensure current_redemptions doesn't exceed max_redemptions
ALTER TABLE public.advantages 
ADD CONSTRAINT check_redemptions_limit 
CHECK (current_redemptions <= max_redemptions);