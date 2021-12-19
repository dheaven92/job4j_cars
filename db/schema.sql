CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL,
  email TEXT NOT NULL,
  password TEXT NOT NULL,
  created TIMESTAMP DEFAULT now(),
  updated TIMESTAMP DEFAULT now()
);

CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    brand TEXT NOT NULL,
    model TEXT NOT NULL,
    body_type TEXT NOT NULL,
    photo TEXT,
    description TEXT,
    sold BOOLEAN DEFAULT false,
    user_id INT NOT NULL REFERENCES users (id),
    created TIMESTAMP DEFAULT now(),
    updated TIMESTAMP DEFAULT now()
);