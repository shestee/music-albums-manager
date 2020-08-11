package com.shestee.albums.service;

import com.shestee.albums.dao.SongRepository;
import com.shestee.albums.entity.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private SongRepository songRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public List<Song> getAllSongs() {
        return null;
    }

    @Override
    public List<Song> findByAlbumId(int albumId) {
        List<Song> songs = songRepository.findByAlbumIdOrderByTrackNumber(albumId);
        return songs;
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
        songRepository.save(song);
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
