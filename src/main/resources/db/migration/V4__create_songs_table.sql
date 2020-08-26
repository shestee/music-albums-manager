create table songs (
	id int primary key auto_increment,
	track_number varchar(6),
	title varchar(160) not null,
	music varchar(100),
	lyrics varchar(100),
	sheet_album_id int,
	album_id int not null,
    constraint fk_album foreign key (album_id)
    references albums(id)
);