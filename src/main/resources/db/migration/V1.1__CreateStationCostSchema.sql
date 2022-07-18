CREATE SEQUENCE vt_station_seq start with 1000;

CREATE TABLE station (
  gas_id bigint PRIMARY KEY DEFAULT nextval('vt_station_seq'),
  vendor varchar(40),
  street varchar(40),
  city_state varchar(40)
);

CREATE TABLE price (
  vendor varchar(40),
  grade int,
  price decimal(5, 2),
  retrieved_date timestamp 
);


