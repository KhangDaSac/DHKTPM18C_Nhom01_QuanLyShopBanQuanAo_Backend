-- Fix customer table to allow null user_id for guest customers
-- Run this SQL script in your database

ALTER TABLE customers MODIFY COLUMN user_id VARCHAR(255) NULL;
