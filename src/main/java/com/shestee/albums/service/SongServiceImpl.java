package com.shestee.albums.service;

import com.shestee.albums.entity.Song;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Override
    public List<Song> getAllSongs() {
        return null;
    }

    @Override
    public Song getById(int id) {
        return null;
    }

    @Override
    public List<Song> findByTitle(String title) {
        return null;
    }

    @Override
    public List<Song> findByMusic(String music) {
        return null;
    }

    @Override
    public List<Song> findByLyrics(String lyrics) {
        return null;
    }

    @Override
    public void addSong(Song song) {

    }

    @Override
    public void removeSongById(int id) {

    }

    @Override
    public String getAlbumBySong(int id) {
        return null;
    }

    @Override
    public void viewSongsByTitle(String title) {

    }

    @Override
    public List<Song> findByArtist(String artist) {
        return null;
    }

    @Override
    public void viewSongs(List<Song> songs) {

    }

    @Override
    public void viewSongInfoById(int id) {

    }
}
