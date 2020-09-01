package com.shestee.albums.service;

import com.shestee.albums.config.AuthenticationFacade;
import com.shestee.albums.dao.AlbumRepository;
import com.shestee.albums.entity.Album;
import com.shestee.albums.entity.Song;
import com.shestee.albums.entity.User;
import com.shestee.albums.entity.enums.LengthType;
import com.shestee.albums.entity.enums.Medium;
import com.shestee.albums.parsers.AlbumJsonParser;
import com.shestee.albums.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AuthenticationFacade authenticationFacade;

    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    UserService userService;

    @Autowired
    SongService songService;

    private AlbumRepository albumRepository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public List<Album> getAllAlbums() {
        return albumRepository.findAllByOrderByIdDesc();
    }

    @Override
    public List<Album> getUsersAlbums() {
        String userName = authenticationFacade.getAuthentication().getName();
        User user = userService.findByUsername(userName);

        return user.getAlbums();
    }

    @Override
    public List<Album> findByArtistAndFormat(String artist, Medium searchFormatOption) {
        String userName = authenticationFacade.getAuthentication().getName();
        User user = userService.findByUsername(userName);

        return albumRepository.findByArtistContainingIgnoreCaseAndMediumAndUserId(artist, searchFormatOption, user.getId());
    }

    @Override
    public List<Album> findByArtist(String artist) {
        String userName = authenticationFacade.getAuthentication().getName();
        User user = userService.findByUsername(userName);

        return albumRepository.findByArtistContainingIgnoreCaseAndUserId(artist, user.getId());
    }



    @Override
    public Album findById(int id) {
        Optional<Album> result = albumRepository.findById(id);

        Album album = null;

        if (result.isPresent()) {
            album = result.get();
        } else {
            throw new RuntimeException("Did not find album id - " + id);
        }

        return album;
    }

    @Override
    public List<Album> findByTitleAndFormat(String title, Medium searchFormatOption) {
        String userName = authenticationFacade.getAuthentication().getName();
        User user = userService.findByUsername(userName);

        return albumRepository.findByTitleContainingIgnoreCaseAndMediumAndUserId(title, searchFormatOption, user.getId());
    }

    @Override
    public List<Album> findByTitle(String title) {
        String userName = authenticationFacade.getAuthentication().getName();
        User user = userService.findByUsername(userName);

        return albumRepository.findByTitleContainingIgnoreCaseAndUserId(title, user.getId());
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
        String userName = authenticationFacade.getAuthentication().getName();
        User user = userService.findByUsername(userName);

        return albumRepository.findByGenreContainingIgnoreCaseAndUserId(genre, user.getId());
    }

    @Override
    public List<Album> findByGenreAndFormat(String genre, Medium searchFormatOption) {
        String userName = authenticationFacade.getAuthentication().getName();
        User user = userService.findByUsername(userName);

        return albumRepository.findByGenreContainingIgnoreCaseAndMediumAndUserId(genre, searchFormatOption, user.getId());
    }


    @Override
    public void addAlbum(Album album) {
        albumRepository.save(album);
    }

    @Override
    public int getIdBysheetAlbumId(int sheetAlbumId) {
        Album album = getAllAlbums().stream()
                .filter(a -> a.getSheetAlbumId() == sheetAlbumId)
                .findFirst().get();
        return album.getId();
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
            songService.addSong(song);
        }
    }

    @Override
    public void addAllSongsToLastAlbum(String releaseId) {
        List<Song> songs = AlbumJsonParser.parseSongsFromAlbumJson(jsonUtil.getAlbumJson(releaseId));
        for (Song song : songs) {
            song.setAlbum(albumRepository.findTopByOrderByIdDesc());
            songService.addSong(song);
        }
    }
}