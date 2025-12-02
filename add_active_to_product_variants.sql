-- Migration: Add active column to product_variants table
-- Date: 2025-12-02
-- Purpose: Add soft delete functionality for product variants

-- Add active column with default value TRUE
ALTER TABLE product_variants 
ADD COLUMN active BOOLEAN NOT NULL DEFAULT TRUE;

-- Update existing records to be active
UPDATE product_variants 
SET active = TRUE 
WHERE active IS NULL;

-- Add index for better query performance when filtering by active status
CREATE INDEX idx_product_variants_active ON product_variants(active);

-- Add index for filtering by product_id and active status together
CREATE INDEX idx_product_variants_product_active ON product_variants(product_id, active);
