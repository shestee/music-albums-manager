package com.shestee.albums.service;

import com.shestee.albums.dao.AlbumRepository;
import com.shestee.albums.entity.Album;
import com.shestee.albums.entity.Song;
import com.shestee.albums.entity.enums.LengthType;
import com.shestee.albums.entity.enums.Medium;
import com.shestee.albums.parsers.AlbumJsonParser;
import com.shestee.albums.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    SongService songService;

    private AlbumRepository albumRepository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    @Override
    public List<Album> findByArtist(String artist) {
        return null;
    }

    @Override
    public Album findById(int id) {
        return null;
    }

    @Override
    public List<Album> findByTitle(String title) {
        return null;
    }

    @Override
    public List<Album> findByYear(int year) {
        return null;
    }

    @Override
    public Album findByCatalogueNumber(String catalogue) {
        return null;
    }

    @Override
    public List<Album> findByMedium(Medium medium) {
        return null;
    }

    @Override
    public List<Album> findByLengthType(LengthType lengthType) {
        return null;
    }

    @Override
    public List<Album> findByGenre(String genre) {
        return null;
    }

    @Override
    public void addAlbum(Album album) {
        albumRepository.save(album);
    }

    @Override
    public int getIdByOwnId(int ownId) {
        return 0;
    }

    @Override
    public List<Song> getSongsFromAlbum(int id) {
        return null;
    }

    @Override
    public void removeAlbum(int id) {
        songService.removeAllSongsFromAlbum(id);
        albumRepository.deleteById(id);
    }

    @Override
    public void addAllSongsToAlbum(int albumId, String releaseID) {
        List<Song> songs = AlbumJsonParser.parseSongsFromAlbumJson(jsonUtil.getAlbumJson(releaseID));
        for (Song song : songs) {
            song.setAlbumId(albumId);
            songService.addSong(song);
        }
    }

    @Override
    public void addAllSongsToLastAlbum(String releaseId) {
        List<Song> songs = AlbumJsonParser.parseSongsFromAlbumJson(jsonUtil.getAlbumJson(releaseId));
        for (Song song : songs) {
            song.setAlbumId(albumRepository.findTopByOrderByIdDesc().getId());
            songService.addSong(song);
        }
    }
}