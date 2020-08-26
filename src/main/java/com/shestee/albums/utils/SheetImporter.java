package com.shestee.albums.utils;

import com.shestee.albums.entity.Album;
import com.shestee.albums.entity.Song;

import java.util.List;

public interface SheetImporter {
    List<Album> copyFromXclToDB();
    List<Song> addSongsFromXCLsheet();
}
