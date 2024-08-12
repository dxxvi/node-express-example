CREATE TABLE technical_metadata (
  id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(255) UNIQUE NOT NULL,
  "comment" VARCHAR(255)
);