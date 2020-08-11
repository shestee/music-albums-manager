package com.shestee.albums.controller;

import com.shestee.albums.entity.Album;
import com.shestee.albums.parsers.AlbumJsonParser;
import com.shestee.albums.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.engine.ModelBuilderTemplateHandler;

import java.util.List;

@Controller
public class AlbumController {

    @Autowired
    private AlbumJsonParser albumParser;

    private AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService theAlbumService) {
        albumService = theAlbumService;
    }

    @GetMapping("/list")
    public String listAlbums(Model theModel) {

        List<Album> albums = albumService.getAllAlbums();

        theModel.addAttribute("albums", albums);

        return "/albums/list-albums";
    }

    @GetMapping("/show-album")
    public String getFromDiscogs(@RequestParam("id") String id, Model theModel) {

        List<Album> albums = albumService.getAllAlbums();

        albums.add(albumParser.parseAlbumFromAlbumJson(id));

        theModel.addAttribute("albums", albums);

        return "/albums/list-albums";
    }

   /* @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Album album = new Album();

        theModel.addAttribute("album", album);

        return "album-form";
    }*/
}
