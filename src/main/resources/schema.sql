create table user(
   id integer not null,
   username varchar(255) not null,
   password varchar(100) not null,
   primary key(id)
);

create table beer
(
   id integer not null,
   name varchar(255) not null,
   description varchar(1024) null,
   primary key(id)
);

create table temperature
(
   id integer not null,
   value integer not null,
   unit varchar(12) null,
   duration integer not null,
   beer_id integer not null,
   primary key(id),
   foreign key (beer_id) references beer(id)
);

create table provider
(
   id integer not null,
   system_id varchar(100) not null,
   beer_id integer not null,
   primary key(id),
   foreign key (beer_id) references beer(id)
);


