package com.shestee.albums.controller;

import com.shestee.albums.entity.Album;
import com.shestee.albums.entity.Song;
import com.shestee.albums.parsers.AlbumJsonParser;
import com.shestee.albums.service.AlbumService;
import com.shestee.albums.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.engine.ModelBuilderTemplateHandler;

import java.util.List;

@Controller
public class AlbumController {

    private static String discogsId;
    //private static Album album;

    @Autowired
    private AlbumJsonParser albumParser;

    @Autowired
    SongService songService;

    private AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService theAlbumService) {
        albumService = theAlbumService;
    }

    @GetMapping("/list")
    public String listAlbums(Model theModel, Model theModel2) {

        List<Album> albums = albumService.getAllAlbums();
        Album album = new Album();

        theModel2.addAttribute("album", album);

        theModel.addAttribute("albums", albums);

        return "/albums/list-albums";
    }

    /*@GetMapping("/show-album")
    public String getFromDiscogs(@RequestParam("id") String id, Model theModel) {
        discogsId = id;

        album = albumParser.parseAlbumFromAlbumJson(id);

        theModel.addAttribute("album", album);
        albumService.addAlbum(album);
        albumService.addAllSongsToAlbum(album.getId(), discogsId);

        return "albums/show-album";
    }*/

    @PostMapping("/add")
    public String addAlbum(@ModelAttribute("album") Album album) {
        albumService.addAlbum(album);
        /*if (discogsId != null) {
            albumService.addAllSongsToAlbum(album.getId(), discogsId);
        }
*/
        return "redirect:/list";
    }

    @GetMapping("/showSongs")
    public String showSongsFromAlbum(@RequestParam("albumId") int albumId, Model theModel) {
        List<Song> songs = songService.findByAlbumId(albumId);

        theModel.addAttribute("songs", songs);

        return "albums/show-songs";
    }

   @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Album album = new Album();

        theModel.addAttribute("album", album);

        return "/albums/album-form";
    }
}
