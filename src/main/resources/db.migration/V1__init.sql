CREATE TABLE IF NOT EXISTS users(
   id_user BIGSERIAL NOT NULL PRIMARY KEY,
   login varchar(25) NOT NULL,
   password varchar(25) NOT NULL,
   roles smallint NOT NULL
);
