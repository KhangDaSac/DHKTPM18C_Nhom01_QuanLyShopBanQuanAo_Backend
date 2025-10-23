-- Cleanup script: Drop invalid_tokens table if exists
-- This table is no longer needed since we're using Redis for token blacklist

-- Drop table if it exists (safe operation)
DROP TABLE IF EXISTS invalid_tokens;

-- Optional: Show confirmation
SELECT 'invalid_tokens table dropped successfully' AS message;
