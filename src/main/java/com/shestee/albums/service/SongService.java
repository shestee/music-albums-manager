package com.shestee.albums.service;

import com.shestee.albums.entity.Song;
import java.util.List;

public interface SongService {
    List<Song> getAllSongs();

    Song getById(int id);

    List<Song> findByTitle(String title);

    List<Song> findByMusic(String music);

    List<Song> findByLyrics(String lyrics);

    void addSong(Song song);

    void removeSongById(int id);

    String getAlbumBySong(int id);

    void viewSongsByTitle(String title);

    List<Song> findByArtist(String artist);

    void viewSongs(List<Song> songs);

    void viewSongInfoById(int id);

    //public List<Song> findSongsByArtist(String artist);
}