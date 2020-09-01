package com.shestee.albums.dao;

import com.shestee.albums.entity.Album;
import com.shestee.albums.entity.enums.Medium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

    Album findTopByOrderByIdDesc();

    List<Album> findAllByOrderByIdDesc();

    //@Query("SELECT a FROM Album a where lower(a.title) like lower concat('%',?1,'%')")
    List<Album> findByTitleContainingIgnoreCaseAndMediumAndUserId(String title, Medium searchFormatOption,
                                                                  int userId);

    List<Album> findByTitleContainingIgnoreCaseAndUserId(String title, int userId);

    List<Album> findByArtistContainingIgnoreCaseAndMediumAndUserId(String artist, Medium searchFormatOption,
                                                                  int userId);

    List<Album> findByArtistContainingIgnoreCaseAndUserId(String artist, int userId);

    List<Album> findByGenreContainingIgnoreCaseAndUserId(String artist, int userId);

    List<Album> findByGenreContainingIgnoreCaseAndMediumAndUserId(String artist, Medium searchFormatOption, int userId);

}
