DROP TABLE IF EXISTS wordle_user, game, guess, letter, letter_result CASCADE;

CREATE TABLE wordle_user (
	user_id serial,
	username VARCHAR(50) UNIQUE NOT NULL,
	password_hash VARCHAR(200) NOT NULL,
	CONSTRAINT PK_wordle_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

CREATE TABLE game (
	game_id serial,
	user_id int,
	game_date date,
	CONSTRAINT PK_game PRIMARY KEY (game_id),
	CONSTRAINT FK_game_user_id FOREIGN KEY (user_id) REFERENCES wordle_user(user_id)
);

CREATE TABLE guess (
	guess_id serial,
	game_id int NOT NULL,
	guess_number int NOT NULL,
	CONSTRAINT PK_guess PRIMARY KEY (guess_id),
	CONSTRAINT FK_guess_game_id FOREIGN KEY (game_id) REFERENCES game(game_id)
);

CREATE TABLE letter_result (
	description VARCHAR(10) UNIQUE NOT NULL,
	CONSTRAINT PK_letter_result PRIMARY KEY (description)
);

CREATE TABLE letter(
	guess_id int NOT NULL,
	position int NOT NULL,
	result VARCHAR(10),
	CONSTRAINT PK_letter PRIMARY KEY (guess_id, position),
	CONSTRAINT FK_letter_guess_id FOREIGN KEY (guess_id) REFERENCES guess(guess_id),
	CONSTRAINT FK_letter FOREIGN KEY (result) REFERENCES letter_result(description)
);


INSERT INTO letter_result (description)
VALUES
	('BLACK'),
	('YELLOW'),
	('GREEN');
