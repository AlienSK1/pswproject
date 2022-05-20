CREATE SCHEMA  products;

CREATE SEQUENCE products.product_seq;


CREATE TABLE products.product (
                                  id INTEGER DEFAULT NEXTVAL ('products.product_seq') PRIMARY KEY,
                                  name VARCHAR(50),
                                  bar_code VARCHAR(70),
                                  price FLOAT,
                                  quantity INT
);