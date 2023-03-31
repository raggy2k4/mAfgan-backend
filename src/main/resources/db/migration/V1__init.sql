CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT      NOT NULL,
    login    VARCHAR(25) NOT NULL,
    password VARCHAR(25) NOT NULL,
    role    SMALLINT    NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_user_role
(
    user_id  BIGINT      NOT NULL,
    role    SMALLINT    NOT NULL,
    PRIMARY KEY (user_id)
);