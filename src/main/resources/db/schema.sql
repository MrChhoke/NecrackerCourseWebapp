CREATE SEQUENCE IF NOT EXISTS id_user_sequence START 1 INCREMENT 1;
CREATE SEQUENCE IF NOT EXISTS id_feedback_sequence START 1 INCREMENT 1;
CREATE SEQUENCE IF NOT EXISTS id_cart_item_sequence START 1 INCREMENT 1;
CREATE SEQUENCE IF NOT EXISTS id_product_item_sequence START 1 INCREMENT 1;
CREATE SEQUENCE IF NOT EXISTS id_shopping_cart_sequence START 1 INCREMENT 1;

CREATE TABLE IF NOT EXISTS user_of_shop
(
    id       INT8         NOT NULL DEFAULT (nextval('id_user_sequence')),
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS roles
(
    user_of_shop_id INT8 NOT NULL,
    roles           VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS product
(
    id          INT8 PRIMARY KEY DEFAULT (nextval('id_product_item_sequence')),
    active      BOOLEAN,
    category    VARCHAR(255),
    description VARCHAR(300),
    name        VARCHAR(30),
    id_photo    TEXT,
    price       FLOAT8 NOT NULL CHECK (price >= 1)
);

CREATE TABLE IF NOT EXISTS shopping_cart
(
    id             INT8 PRIMARY KEY NOT NULL DEFAULT (nextval('id_shopping_cart_sequence')),
    active         BOOLEAN,
    buyer_name     VARCHAR(255),
    complied_order BOOLEAN,
    date           DATE,
    location       VARCHAR(255),
    token_session  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS cart_item
(
    id         INT8 NOT NULL PRIMARY KEY DEFAULT (nextval('id_cart_item_sequence')),
    amount     INT4 NOT NULL,
    date       DATE,
    product_id INT8,
    shopping_cart_id INT8,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart(id)
);

CREATE TABLE IF NOT EXISTS feed_back
(
    id              INT8 PRIMARY KEY DEFAULT (nextval('id_feedback_sequence')),
    date            DATE,
    feed_back_text  VARCHAR(1500),
    user_of_shop_id INT8,
    CONSTRAINT fk_user FOREIGN KEY (user_of_shop_id) REFERENCES user_of_shop (id)
);


