CREATE TABLE IF NOT EXISTS user_account (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS room (
    room_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    users_count INTEGER NOT NULL DEFAULT 0,
    description VARCHAR(100),
    owner VARCHAR(100) REFERENCES user_account(username)
);

