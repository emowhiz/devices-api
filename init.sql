/**
 * DB initialization script
 */

CREATE TABLE IF NOT EXISTS device
(
    id  BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)   NOT NULL,
    brand VARCHAR(255)   NOT NULL,
    state VARCHAR(100)   NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

