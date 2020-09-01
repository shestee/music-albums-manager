package com.shestee.albums.dao;

import com.shestee.albums.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

    Album findTopByOrderByIdDesc();

    List<Album> findAllByOrderByIdDesc();

    //@Query("SELECT a FROM Album a where lower(a.title) like lower concat('%',?1,'%')")
    List<Album> findByTitleContainingIgnoreCaseAndUserId(String title, int userId);

    List<Album> findByArtistContainingIgnoreCaseAndUserId(String artist, int userId);

    List<Album> findByGenreContainingIgnoreCaseAndUserId(String artist, int userId);

}
