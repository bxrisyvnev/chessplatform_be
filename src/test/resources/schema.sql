DROP TABLE IF EXISTS admin_entity;
DROP TABLE IF EXISTS professional_player_entity;
DROP TABLE IF EXISTS spectator_player_entity;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS articles;
DROP TABLE IF EXISTS officialnews;
DROP TABLE IF EXISTS streams;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       age INT NOT NULL,
                       display_name VARCHAR(255) NOT NULL,
                       nationality VARCHAR(255) NOT NULL
);

CREATE TABLE admin_entity (
                              id INT PRIMARY KEY,
                              monthly_salary DOUBLE NOT NULL,
                              contract_start_date DATE NOT NULL,
                              contract_end_date DATE NOT NULL,
                              address VARCHAR(255) NOT NULL,
                              FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE spectator_player_entity (
                                         id INT PRIMARY KEY,
                                         player_elo INT NOT NULL,
                                         is_chat_banned BOOLEAN NOT NULL,
                                         is_game_banned BOOLEAN NOT NULL,
                                         no_of_games_played INT NOT NULL,
                                         has_pass BOOLEAN NOT NULL,
                                         FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE professional_player_entity (
                                            id INT PRIMARY KEY,
                                            player_elo INT NOT NULL,
                                            win_rate DOUBLE NOT NULL,
                                            no_of_games_played INT NOT NULL,
                                            follower_count INT NOT NULL,
                                            FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE articles (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          article_title VARCHAR(255),
                          image_url VARCHAR(255),
                          content_text TEXT,
                          author_id INT NOT NULL,
                          FOREIGN KEY (author_id) REFERENCES users(id)
);

CREATE TABLE comments (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          text VARCHAR(255),
                          user_id INT NOT NULL,
                          article_id INT NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES users(id),
                          FOREIGN KEY (article_id) REFERENCES articles(id)
);

CREATE TABLE officialnews (
                              id INT PRIMARY KEY AUTO_INCREMENT,
                              title VARCHAR(255),
                              link VARCHAR(255),
                              published_date TIMESTAMP,
                              description TEXT,
                              author VARCHAR(255)
);

CREATE TABLE streams (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(255),
                         creation_date_time TIMESTAMP,
                         stream_url TEXT,
                         streamer_id INT NOT NULL,
                         FOREIGN KEY (streamer_id) REFERENCES users(id)
);
