package com.shestee.albums.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;

    @Column(name = "track_number")
    private String trackNumber;

    private String title;
    private String music;
    private String lyrics;

    @Column(name = "sheet_album_id")
    private int sheetAlbumId;

    @Column(name = "album_id")
    private int albumId;

    public Song() {
    }

    public Song(String trackNumber, String title) {
        this.trackNumber = trackNumber;
        this.title = title;
    }

/*    public Song(int id, String trackNumber, String title, String music, String lyrics, int albumId) {
        this.id = id;
        this.trackNumber = trackNumber;
        this.title = title;
        this.music = music;
        this.lyrics = lyrics;
        this.albumId = albumId;
    }*/

    public Song(String trackNumber, String title, String music, String lyrics, int albumId) {
        this.trackNumber = trackNumber;
        this.title = title;
        this.music = music;
        this.lyrics = lyrics;
        this.albumId = albumId;
    }

    // constructor for sheet importer
    public Song(String trackNumber, String title, String music, String lyrics, int sheetAlbumId, int albumId) {
        this.trackNumber = trackNumber;
        this.title = title;
        this.music = music;
        this.lyrics = lyrics;
        this.sheetAlbumId = sheetAlbumId;
        this.albumId = albumId;
    }

    public int getId() {
        return id;
    }

    public Song setId(int id) {
        this.id = id;
        return this;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public Song setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Song setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getMusic() {
        return music;
    }

    public Song setMusic(String music) {
        this.music = music;
        return this;
    }

    public String getLyrics() {
        return lyrics;
    }

    public Song setLyrics(String lyrics) {
        this.lyrics = lyrics;
        return this;
    }

    public int getSheetAlbumId() {
        return sheetAlbumId;
    }

    public void setSheetAlbumId(int sheetAlbumId) {
        this.sheetAlbumId = sheetAlbumId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public Song setAlbumId(int albumId) {
        this.albumId = albumId;
        return this;
    }

    @Override
    public String toString() {
        return "Song title: \"" + title.trim() +
                "\", track on album: " + trackNumber +
                ", music: " + music +
                ", lyrics: " + lyrics +
                " (id=" + id +
                ", albumId=" + albumId + ");\n";
    }
}