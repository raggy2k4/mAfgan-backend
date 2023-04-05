INSERT INTO users(id, login, password, role) VALUES(1, 'loginADMIN', 'passwordADMIN', 0);
INSERT INTO users(id, login, password, role) VALUES(2, 'loginUSER', 'passwordUSER', 1);
INSERT INTO user_user_role(user_id, role) VALUES(1, 0);
INSERT INTO user_user_role(user_id, role) VALUES(2, 1);
INSERT INTO persons(id, nick, team, location) VALUES(1, 'nickPerson1', 'teamPerson1', 'locationPerson1');
INSERT INTO persons(id, nick, team, location) VALUES(2, 'nickPerson2', 'teamPerson2', 'locationPerson2');