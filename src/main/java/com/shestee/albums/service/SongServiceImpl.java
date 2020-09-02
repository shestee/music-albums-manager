package com.shestee.albums.service;

import com.shestee.albums.dao.SongRepository;
import com.shestee.albums.entity.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Song getSongById(int id) {
        Optional<Song> result = songRepository.findById(id);
        Song song = null;

        if (result.isPresent()) {
            song = result.get();
        } else {
            throw new RuntimeException("Did not find song id - " + id);
        }

        return song;
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
        Song song = getSongById(id);
        songRepository.delete(song);
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

    @Override
    public void removeAllSongsFromAlbum(int albumId) {
        List<Song> songsToRemove = findByAlbumId(albumId);

        for (Song song : songsToRemove) {
            songRepository.delete(song);
        }
    }
}
