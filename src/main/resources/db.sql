CREATE SCHEMA  product;
CREATE SEQUENCE product.product_seq;
CREATE TABLE product.products (
      id INTEGER DEFAULT NEXTVAL ('product.product_seq') PRIMARY KEY,
      name VARCHAR(50),
      bar_code VARCHAR(70),
      price DOUBLE PRECISION,
      quantity INT,
      image varchar
);

CREATE SCHEMA utente;
CREATE SEQUENCE utente.user_seq;
CREATE TABLE utente.utenti (
    id INTEGER DEFAULT  nextval('utente.user_seq') PRIMARY KEY,
    firstname varchar(50),
    lastname varchar(50),
    username varchar(50),
    email varchar(100),
    address varchar(100)
);

CREATE SCHEMA cart;
Create SEQUENCE cart.cart_seq;
Create TABLE cart.carts(
    id INTEGER DEFAULT nextval('cart.cart_seq') PRIMARY KEY ,
    utente INTEGER references utente.utenti(id),
    totalPrice DOUBLE PRECISION
);

CREATE  sequence cart.productInCart_seq;
CREATE TABLE cart.productincart(
    id INTEGER DEFAULT nextval('cart.productInCart_seq') PRIMARY KEY,
    cart INTEGER references cart.carts(id),
    product INTEGER references product.products(id),
    quantity INTEGER
);

CREATE SCHEMA ordine;
CREATE SEQUENCE ordine.order_seq;
CREATE TABLE ordine.ordini(
    id INTEGER DEFAULT nextval('ordine.order_seq') PRIMARY KEY,
    utente INTEGER  references utente.utenti(id),
    data DATE,
    totalprice float8
);