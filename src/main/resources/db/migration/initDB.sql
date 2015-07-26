CREATE SEQUENCE IF NOT EXISTS SEQ_USER_TABLE;
DROP TABLE IF EXISTS user;
CREATE TABLE user (
  ID       INT DEFAULT (NEXT VALUE FOR SEQ_USER_TABLE) PRIMARY KEY NOT NULL,
  login    VARCHAR(255)                                            NOT NULL,
  password VARCHAR(255)                                            NOT NULL,
  name     VARCHAR(255)                                            NOT NULL,
  email    VARCHAR(255)                                            NOT NULL,
  role     VARCHAR(255)                                            NOT NULL,
  active   BOOLEAN DEFAULT TRUE                                    NOT NULL
);
ALTER TABLE user ADD CONSTRAINT LOGIN_UNIQUE UNIQUE (login);
ALTER TABLE user ADD CONSTRAINT EMAIL_UNIQUE UNIQUE (email);
INSERT INTO user VALUES (1, 'user', 'user', 'user name', 'user@system.com', 'ROLE_USER', TRUE);
INSERT INTO user VALUES (2, 'admin', 'admin', 'admin name', 'admin@system.com', 'ROLE_ADMIN', TRUE);
INSERT INTO user VALUES (3, 'user2', 'user2', 'user2 name', 'user2@system.com', 'ROLE_USER', TRUE);
INSERT INTO user VALUES (4, 'admi3', 'admi3', 'admi3 name', 'admi3@system.com', 'ROLE_USER', TRUE);

CREATE SEQUENCE IF NOT EXISTS SEQ_MESS_TABLE;
DROP TABLE IF EXISTS message;
CREATE TABLE message (
  ID             INT DEFAULT (NEXT VALUE FOR SEQ_MESS_TABLE)  PRIMARY KEY NOT NULL,
  create_date    TIMESTAMP                                                NOT NULL,
  subject        VARCHAR(255),
  body           VARCHAR(255)                                             NOT NULL,
  author_user_id INT                                                      NOT NULL,
  to_user_id     INT                                                      NOT NULL,
  FOREIGN KEY (author_user_id) REFERENCES user (ID),
  FOREIGN KEY (to_user_id) REFERENCES user (ID)
);

INSERT INTO message VALUES (1, '2015-07-11 01:01:01', 'тема', 'сообщение', 2, 1);
INSERT INTO message VALUES (2, '2015-07-12 01:01:01', 'тема', 'сообщение 222222', 2, 1);
INSERT INTO message VALUES (3, '2015-07-13 01:01:01', 'тема', 'сообщение222222', 2, 1);
INSERT INTO message VALUES (4, '2015-07-15 01:01:01', 'тема', 'сообщение2 2 22', 2, 1);
INSERT INTO message VALUES (5, '2015-07-11 01:01:01', 'тема', 'сообщение 22 2', 2, 1);
INSERT INTO message VALUES (6, '2015-07-01 01:01:01', 'тема', 'сообщение 2 2', 2, 1);
INSERT INTO message VALUES (8, '2015-07-10 01:01:01', 'тема', 'сообщение 2 2', 2, 1);

DROP TABLE IF EXISTS user_list;
CREATE TABLE user_list (
  ID         INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  user_id    INT                            NOT NULL,
  to_user_id INT                            NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user (ID),
  FOREIGN KEY (to_user_id) REFERENCES user (ID)
);

INSERT INTO user_list VALUES (1, 1, 2);
INSERT INTO user_list VALUES (2, 2, 1);