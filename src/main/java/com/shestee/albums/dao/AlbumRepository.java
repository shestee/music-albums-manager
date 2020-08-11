package com.shestee.albums.dao;

import com.shestee.albums.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

    // no need to write any code, it works just like this

    Album findTopByOrderByIdDesc();

}
