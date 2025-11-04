-- Add district and fullAddress columns to addresses table
ALTER TABLE addresses 
ADD COLUMN district VARCHAR(255),
ADD COLUMN full_address VARCHAR(500);

-- Update existing addresses to have fullAddress
UPDATE addresses 
SET full_address = CONCAT_WS(', ', address_detail, ward, city)
WHERE full_address IS NULL;
