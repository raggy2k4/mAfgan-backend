CREATE TABLE IF NOT EXISTS users (
    id       BIGINT      NOT NULL,
    login    VARCHAR(25) NOT NULL,
    password VARCHAR(25) NOT NULL,
    role    SMALLINT    NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_user_role (
    user_id  BIGINT      NOT NULL,
    role    SMALLINT    NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS persons (
    id       BIGINT      NOT NULL,
    nick    VARCHAR(25) NOT NULL,
    team    VARCHAR(25) NOT NULL,
    location VARCHAR(25) NOT NULL,
    PRIMARY KEY (id)
);