package com.shestee.albums.dao;

import com.shestee.albums.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

    // no need to write any code, it works just like this

    Album findTopByOrderByIdDesc();

    List<Album> findAllByOrderByIdDesc();

}
