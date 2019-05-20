-- DATABASE CREATION SCRIPT

-- Database: ebiznes

-- DROP DATABASE ebiznes;

CREATE DATABASE ebiznes
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Polish_Poland.1250'
       LC_CTYPE = 'Polish_Poland.1250'
       CONNECTION LIMIT = -1;
	   
-- Schema: public

-- DROP SCHEMA public;

CREATE SCHEMA public
  AUTHORIZATION postgres;

GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;
COMMENT ON SCHEMA public
  IS 'standard public schema';
  
  
  -- Table: public."user"

-- DROP TABLE public."user";

CREATE TABLE public."user"
(
  user_id bigint NOT NULL,
  email_address character varying(255),
  display_name character varying(75),
  password character varying(75),
  first_name character varying,
  last_name character varying,
  address_1 character varying,
  address_2 character varying,
  phone_number character varying,
  verified boolean,
  password_reset boolean,
  created_date timestamp without time zone,
  modified_date timestamp without time zone,
  payment_info character varying,
  CONSTRAINT user_pk PRIMARY KEY (user_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."user"
  OWNER TO postgres;

  
  -- Table: public.category

-- DROP TABLE public.category;

CREATE TABLE public.category
(
  category_id bigint NOT NULL,
  category_name character varying(75),
  CONSTRAINT category_pk PRIMARY KEY (category_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.category
  OWNER TO postgres;

  
-- Table: public.product

-- DROP TABLE public.product;

CREATE TABLE public.product
(
  product_id bigint NOT NULL,
  name character varying(75),
  category_id bigint,
  price real,
  short_description character varying(500),
  details character varying,
  available boolean,
  available_quantity integer,
  CONSTRAINT product_pkey PRIMARY KEY (product_id),
  CONSTRAINT product_fkey FOREIGN KEY (category_id)
      REFERENCES public.category (category_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.product
  OWNER TO postgres;

-- Index: public.fki_product_fkey

-- DROP INDEX public.fki_product_fkey;

CREATE INDEX fki_product_fkey
  ON public.product
  USING btree
  (category_id);

-- Table: public.product_details

-- DROP TABLE public.product_details;

CREATE TABLE public.product_details
(
  pd_id bigint NOT NULL,
  product_id bigint NOT NULL,
  full_description text,
  CONSTRAINT product_details_pkey PRIMARY KEY (pd_id),
  CONSTRAINT product_details_fkey FOREIGN KEY (product_id)
      REFERENCES public.product (product_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.product_details
  OWNER TO postgres;

-- Index: public.fki_product_details_fkey

-- DROP INDEX public.fki_product_details_fkey;

CREATE INDEX fki_product_details_fkey
  ON public.product_details
  USING btree
  (product_id);

  -- Table: public.product_image

-- DROP TABLE public.product_image;

CREATE TABLE public.product_image
(
  p_image_id bigint NOT NULL,
  product_id bigint NOT NULL,
  height smallint,
  width smallint,
  size integer,
  CONSTRAINT product_image_pk PRIMARY KEY (p_image_id),
  CONSTRAINT product_image_fkey FOREIGN KEY (product_id)
      REFERENCES public.product (product_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.product_image
  OWNER TO postgres;

-- Index: public.fki_product_image_fkey

-- DROP INDEX public.fki_product_image_fkey;

CREATE INDEX fki_product_image_fkey
  ON public.product_image
  USING btree
  (product_id);

-- Table: public."order"

-- DROP TABLE public."order";

CREATE TABLE public."order"
(
  order_id bigint NOT NULL,
  user_id bigint NOT NULL,
  status character varying,
  total_price double precision,
  CONSTRAINT order_pk PRIMARY KEY (order_id),
  CONSTRAINT order_fkey FOREIGN KEY (user_id:)
      REFERENCES public."user" (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."order"
  OWNER TO postgres;

-- Index: public.fki_order_fkey

-- DROP INDEX public.fki_order_fkey;

CREATE INDEX fki_order_fkey
  ON public."order"
  USING btree
  (user_id);

 -- Table: public.order_item

-- DROP TABLE public.order_item;

CREATE TABLE public.order_item
(
  order_item_id bigint NOT NULL,
  order_id bigint NOT NULL,
  product_id bigint,
  quantity smallint,
  total_price double precision,
  CONSTRAINT order_item_pk PRIMARY KEY (order_item_id),
  CONSTRAINT order_item_fkey FOREIGN KEY )order_id)
      REFERENCES public."order" (order_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.order_item
  OWNER TO postgres;

-- Index: public.fki_order_item_fkey

-- DROP INDEX public.fki_order_item_fkey;

CREATE INDEX fki_order_item_fkey
  ON public.order_item
  USING btree
  (order_id);

  -- Table: public.cart

-- DROP TABLE public.cart;

CREATE TABLE public.cart
(
  cart_id bigint NOT NULL,
  user_id bigint NOT NULL,
  total_price double precision,
  CONSTRAINT cart_pk PRIMARY KEY (cart_id),
  CONSTRAINT user_id FOREIGN KEY (user_id)
      REFERENCES public."user" (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.cart
  OWNER TO postgres;

-- Index: public.fki_user_id

-- DROP INDEX public.fki_user_id;

CREATE INDEX fki_user_id
  ON public.cart
  USING btree
  (user_id);


 -- Table: public.cart_item

-- DROP TABLE public.cart_item;

CREATE TABLE public.cart_item
(
  cart_item_id bigint NOT NULL,
  product_id bigint NOT NULL,
  quantity smallint,
  price_total double precision,
  cart_id bigint NOT NULL,
  CONSTRAINT cart_item_pk PRIMARY KEY (cart_item_id),
  CONSTRAINT cart_item_fkey FOREIGN KEY (cart_id)
      REFERENCES public.cart (cart_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.cart_item
  OWNER TO postgres;

-- Index: public.fki_cart_item_fkey

-- DROP INDEX public.fki_cart_item_fkey;

CREATE INDEX fki_cart_item_fkey
  ON public.cart_item
  USING btree
  (cart_id);




