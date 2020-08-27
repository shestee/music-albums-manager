package com.shestee.albums.dao;

import com.shestee.albums.entity.Album;
import com.shestee.albums.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Integer> {

    List<Song> findByAlbumIdOrderByTrackNumber(int albumId);
}
