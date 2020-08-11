create table songs (
	id int primary key auto_increment,
	track_number varchar(6),
	title varchar(160) not null,
	music varchar(100),
	lyrics varchar(100),
	album_id int not null,
    foreign key (album_id) references albums(id)
);