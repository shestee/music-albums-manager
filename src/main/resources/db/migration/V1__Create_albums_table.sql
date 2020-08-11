create table albums (
	id int primary key auto_increment,
	artist varchar(100) not null,
	title varchar(160) not null,
	medium enum('VINYL','CD','CASSETTE','FILES', 'OTHER') not null,
	length_type enum('LP','EP','SINGLE', 'OTHER') not null,
	genre varchar(100) not null,
	label varchar(100) not null,
	catalogue varchar(30) not null,
	year int(11) not null,
	own_id int(11) not null default -1
);