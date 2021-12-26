CREATE TABLE users (
    id       SERIAL PRIMARY KEY,
    name     TEXT NOT NULL,
    email    TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    created  TIMESTAMP DEFAULT now(),
    updated  TIMESTAMP DEFAULT now()
);

CREATE TABLE brand (
    id      SERIAL PRIMARY KEY,
    name    TEXT NOT NULL,
    created TIMESTAMP DEFAULT now(),
    updated TIMESTAMP DEFAULT now()
);

INSERT INTO brand (name) VALUES ('Chevrolet');
INSERT INTO brand (name) VALUES ('Toyota');
INSERT INTO brand (name) VALUES ('Nissan');
INSERT INTO brand (name) VALUES ('Hyundai');
INSERT INTO brand (name) VALUES ('BMW');
INSERT INTO brand (name) VALUES ('Mercedes-Benz');

CREATE TABLE body_type (
    id      SERIAL PRIMARY KEY,
    name    TEXT NOT NULL,
    created TIMESTAMP DEFAULT now(),
    updated TIMESTAMP DEFAULT now()
);

INSERT INTO body_type (name) VALUES ('Седан');
INSERT INTO body_type (name) VALUES ('Универсал');
INSERT INTO body_type (name) VALUES ('Хэтчбек');
INSERT INTO body_type (name) VALUES ('Купе');

CREATE TABLE car (
    id           SERIAL PRIMARY KEY,
    brand_id     INT  NOT NULL REFERENCES brand (id),
    model        TEXT NOT NULL,
    body_type_id INT  NOT NULL REFERENCES body_type (id),
    created      TIMESTAMP DEFAULT now(),
    updated      TIMESTAMP DEFAULT now()
);

CREATE TABLE post (
    id          SERIAL PRIMARY KEY,
    car_id      INT REFERENCES car (id),
    description TEXT,
    sold        BOOLEAN   DEFAULT false,
    user_id     INT NOT NULL REFERENCES users (id),
    created     TIMESTAMP DEFAULT now(),
    updated     TIMESTAMP DEFAULT now()
);