package com.shestee.albums.service;

import com.shestee.albums.entity.Album;
import com.shestee.albums.entity.Song;
import com.shestee.albums.entity.enums.LengthType;
import com.shestee.albums.entity.enums.Medium;

import java.util.List;

public interface AlbumService {
    List<Album> getAllAlbums();

    List<Album> getUsersAlbums();

    List<Album> findByArtistAndFormat(String artist, Medium searchFormatOption);
    List<Album> findByArtist(String artist);

    Album findById(int id);

    List<Album> findByTitleAndFormat(String title, Medium searchFormatOption);
    List<Album> findByTitle(String title);

    List<Album> findByYear(int year);

    Album findByCatalogueNumber(String catalogue);

    List<Album> findByMedium(Medium medium);

    List<Album> findByLengthType(LengthType lengthType);

    List<Album> findByGenre(String genre);
    List<Album> findByGenreAndFormat(String genre, Medium searchFormatOption);

    void addAlbum(Album album);

    int getIdBysheetAlbumId(int sheetAlbumId);

    List<Song> getSongsFromAlbum(int id);

    void removeAlbum(int id);

    void addAllSongsToAlbum(int albumId, String releaseID);

    void addAllSongsToLastAlbum(String releaseId);
}
