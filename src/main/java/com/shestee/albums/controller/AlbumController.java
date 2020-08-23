package com.shestee.albums.controller;

import com.shestee.albums.config.AuthenticationFacade;
import com.shestee.albums.entity.Album;
import com.shestee.albums.entity.Song;
import com.shestee.albums.entity.User;
import com.shestee.albums.parsers.AlbumJsonParser;
import com.shestee.albums.service.AlbumService;
import com.shestee.albums.service.SongService;
import com.shestee.albums.service.UserService;
import com.shestee.albums.utils.SheetImporterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AlbumController {

    @Autowired
    AuthenticationFacade authenticationFacade;

    @Autowired
    UserService userService;

    private String discogsId;
    //private static Album album;

    @Autowired
    private SheetImporterImpl sheetImporter;

    @Autowired
    private AlbumJsonParser albumParser;

    @Autowired
    private SongService songService;

    private AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService theAlbumService) {
        albumService = theAlbumService;
    }

    @GetMapping("/list")
    public String listAlbums(Model theModel, Model theModel2) {

        Album album = new Album();

        List<Album> albums = albumService.getUsersAlbums();

        theModel2.addAttribute("album", album);

        theModel.addAttribute("albums", albums);

        return "/albums/list-albums";
    }

    @PostMapping("/add")
    public String addAlbum(@ModelAttribute("album") Album album) {
        User user = userService.findByUsername(authenticationFacade.getAuthentication().getName());
        album.setUserId(user.getId());

        albumService.addAlbum(album);
        if (discogsId != null) {
            albumService.addAllSongsToLastAlbum(discogsId);
        }

        return "redirect:/list";
    }

    @GetMapping("/showSongs")
    public String showSongsFromAlbum(@RequestParam("albumId") int albumId, Model theModel) {
        List<Song> songs = songService.findByAlbumId(albumId);
        theModel.addAttribute("songs", songs);

        Album album = albumService.findById(albumId);
        theModel.addAttribute("album", album);

        return "albums/show-songs";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Album album = new Album();

        theModel.addAttribute("album", album);

        return "/albums/album-form";
    }

    @GetMapping("/showFormForAddDataFromDiscogs")
    public String showFormForAddDataFromDiscogs(@RequestParam("discogsId") String discogsId, Model theModel) {
        this.discogsId = discogsId;

        Album album = albumParser.parseAlbumFromAlbumJson(discogsId);

        theModel.addAttribute("album", album);

        return "/albums/album-form";
    }

    @GetMapping("/delete")
    public String removeAlbumById(@RequestParam("albumId") int id) {
        albumService.removeAlbum(id);

        return "redirect:/list";
    }

    @GetMapping("/importFromSheet")
    public String importFromSheet() {
        sheetImporter.copyFromXclToDB();
        sheetImporter.addSongsFromXCLsheet();

        return "redirect:/list";
    }
}
