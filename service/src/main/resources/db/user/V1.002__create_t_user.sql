CREATE TABLE IF NOT EXISTS t_mt_user (
    id UUID PRIMARY KEY NOT NULL,
    username VARCHAR(25) NOT NULL UNIQUE,
    password VARCHAR(25) NOT NULL,
    salt_password VARCHAR(255) NOT NULL,
    nick_name VARCHAR(25) NOT NULL,
    first_name VARCHAR(25) NOT NULL,
    middle_name VARCHAR(25),
    last_name VARCHAR(25),
    device_ids JSONB null,
    emails JSONB null,
    addresses JSONB null,
    contacts JSONB null,
    roles JSONB NOT NULL,
    status varchar(10) NOT NULL DEFAULT 'A'::varchar,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50) NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50) NOT NULL,
    "version" int4 NOT NULL DEFAULT 1
);

CREATE UNIQUE INDEX IF NOT EXISTS uq_mt_user ON t_mt_user(username, first_name, status)
