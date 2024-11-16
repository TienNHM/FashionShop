-- CREATE SCHEMA fashionshop;

-- Bảng admin
DROP TABLE IF EXISTS admin;
CREATE TABLE admin (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100),
  password VARCHAR(50),
  phone VARCHAR(20)
);

-- Bảng cart
DROP TABLE IF EXISTS cart;
CREATE TABLE cart (
  id SERIAL PRIMARY KEY,
  uid INT,
  pid INT,
  quantity INT
);

-- Bảng category
DROP TABLE IF EXISTS category;
CREATE TABLE category (
  cid SERIAL PRIMARY KEY,
  name VARCHAR(100),
  image VARCHAR(100)
);

-- Bảng order
DROP TABLE IF EXISTS "order";
CREATE TABLE "order" (
  id SERIAL PRIMARY KEY,
  orderid VARCHAR(100),
  status VARCHAR(100),
  paymentType VARCHAR(100),
  userId INT,
  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng ordered_product
DROP TABLE IF EXISTS ordered_product;
CREATE TABLE ordered_product (
  oid SERIAL PRIMARY KEY,
  name VARCHAR(100),
  quantity INT,
  price VARCHAR(45),
  image VARCHAR(100),
  orderid INT
);

-- Bảng product
DROP TABLE IF EXISTS product;
CREATE TABLE product (
  pid SERIAL PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  description VARCHAR(500),
  price VARCHAR(20) NOT NULL,
  quantity INT,
  discount INT,
  image VARCHAR(100),
  cid INT
);

-- Bảng user
DROP TABLE IF EXISTS "user";
CREATE TABLE "user" (
  userid SERIAL PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(45) UNIQUE,
  password VARCHAR(45),
  phone VARCHAR(20) UNIQUE,
  gender VARCHAR(20),
  registerdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  address VARCHAR(250),
  city VARCHAR(100),
  pincode VARCHAR(10),
  state VARCHAR(100)
);

-- Bảng wishlist
DROP TABLE IF EXISTS wishlist;
CREATE TABLE wishlist (
  idwishlist SERIAL PRIMARY KEY,
  iduser INT,
  idproduct INT
);






-- Bảng cart
ALTER TABLE cart
  ADD CONSTRAINT fk_cart_uid FOREIGN KEY (uid) REFERENCES "user" (userid),
  ADD CONSTRAINT fk_cart_pid FOREIGN KEY (pid) REFERENCES product (pid);

-- Bảng order
ALTER TABLE "order"
  ADD CONSTRAINT fk_order_userId FOREIGN KEY (userId) REFERENCES "user" (userid);

-- Bảng ordered_product
ALTER TABLE ordered_product
  ADD CONSTRAINT fk_ordered_product_orderid FOREIGN KEY (orderid) REFERENCES "order" (id);

-- Bảng product
ALTER TABLE product
  ADD CONSTRAINT fk_product_cid FOREIGN KEY (cid) REFERENCES category (cid);

-- Bảng wishlist
ALTER TABLE wishlist
  ADD CONSTRAINT fk_wishlist_iduser FOREIGN KEY (iduser) REFERENCES "user" (userid),
  ADD CONSTRAINT fk_wishlist_idproduct FOREIGN KEY (idproduct) REFERENCES product (pid);






INSERT INTO admin (id, name, email, password, phone) VALUES
(1,'Vi Quoc Thuan','test@gmail.com','111','7755632012'),
(4,'Admin','test34@gmail.com','111','8565452152');


INSERT INTO category (cid, name, image) VALUES
(1,'Mobiles','Mobiles.jpg'),
(2,'Appliances','Appliances.jpg'),
(3,'Laptops','Laptops.jpg'),
(4,'Home & Furniture','Home_Furniture.jpg'),
(5,'Books','Books.jpg'),
(6,'Clothes & Fashion','Clothes_Fashion.jpg'),
(7,'Electronics','Electronics.jpg');


INSERT INTO product (pid, name, description, price, quantity, discount, image, cid) VALUES
(1,'SAMSUNG Galaxy F14 5G','The Samsung Galaxy F14 smartphone uses a segment-only 5nm processor that enables you with easy multitasking, gaming, and much more. It has a 6000 mAh battery that will last you for up to 2 days on a single charge. It has a large display of about 16.72 cm (6.5) full HD+ display that enables you with immersive viewing. The 12 GB of RAM with RAM Plus offers enough storage space to store all your data.','18490.0',9,24,'Samsung.jpg',1),
(2,'LG 242 L Frost Free Double Door  Refrigerator','You can enjoy chilled drinks and popsicles during summer by keeping them stocked up in the LG 242 L Frost-free Double-door Refrigerator. Its Smart Inverter Compressor is designed to deliver energy-efficient performance, thus keeping a check on your electricity bills. Also, this refrigerator?s Door Cooling+ feature keeps the food items fresh and drinks chilled while rendering uniform rapid cooling.','37099.0',50,29,'LG.jpg',2),
(3,'OnePlus Y1S Pro 138 cm  Ultra HD (4K) LED Smart Android TV','Enjoy rich, clear, and authentic audiovisual content in its true form with the brilliant OnePlus TV that understands you and strives to keep you entertained constantly. This OnePlus TV s Smart Manager offers a number of enhancements for a smart and durable TV experience. Thanks to the sophisticated Gamma Engine, which intelligently adjusts the image for crystal-clear content and maximises display quality, every scene comes to life.','49999.0',1,18,'OnePlus.jpg',2),
(8,'Samsung Galaxy S23 5G','Brand Samsung Model Name Samsung Galaxy S23 Network Service Provider Unlocked for All Carriers Operating System Android 13.0 Cellular Technology 5G','79999.0',10,17,'Galaxy_S23_5G.jpg',1),
(9,'ASUS TUF Gaming A15','15.6 inch Full HD, IPS, Anti-glare Display, Aspect Ratio: 16:9, Refresh Rate: 144 Hz, Viewing Angle: 85/85/85/85, Brightness: 250nits, Contrast Ratio: 1:1000, 45% NTSC, SRGB%: 62.5%, Adobe%: 47.34%, Adaptive-Sync Light Laptop without Optical Disk Drive Preloaded with MS Office','71990.0',11,20,'ASUS.jpg',3);



