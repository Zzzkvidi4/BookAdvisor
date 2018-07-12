CREATE TABLE users(
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(20) NOT NULL UNIQUE,
  password VARCHAR(20) NOT NULL
);
CREATE INDEX username_index ON users(username);

CREATE TABLE books(
  book_id INT AUTO_INCREMENT PRIMARY KEY,
  selector VARCHAR(60) NOT NULL UNIQUE
);
CREATE INDEX selector_index ON books(selector);

CREATE TABLE users_books(
  book_id INT NOT NULL,
  user_id INT NOT NULL,
  CONSTRAINT users_books_pk PRIMARY KEY (book_id, user_id)
)