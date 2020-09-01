package com.shestee.albums.controller;

import com.shestee.albums.config.AuthenticationFacade;
import com.shestee.albums.dao.UserRepository;
import com.shestee.albums.dto.SearchDto;
import com.shestee.albums.entity.Album;
import com.shestee.albums.entity.Song;
import com.shestee.albums.entity.User;
import com.shestee.albums.entity.enums.Medium;
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
    UserRepository userRepository;

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

        theModel.addAttribute("searchDto", new SearchDto());

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

        discogsId = null;

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

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("albumId") int albumId, Model theModel) {
        // Get album by id
        Album album = albumService.findById(albumId);

        // add album to the model
        theModel.addAttribute(album);

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
        List<Album> albums = sheetImporter.getAlbumsFromXclSheet();
        List<Song> songs = sheetImporter.getSongsFromXclSheet();

        User user = userService.findByUsername(authenticationFacade.getAuthentication().getName());

        for (Album album : albums) {
            album.setUserId(user.getId());

            int sheetAlbumId = album.getSheetAlbumId();

            songs.stream()
                 .filter(song -> song.getSheetAlbumId() == sheetAlbumId)
                 .forEach(song -> {
                     song.setAlbum(album);
                     songService.addSong(song);
                 });

            albumService.addAlbum(album);
        }

        return "redirect:/list";
    }
    
    @PostMapping("/processSearch")
    public String processSearch(@ModelAttribute("searchDto") SearchDto searchDto, Model theModel) {
        List<Album> foundAlbums = null;

        switch (searchDto.getSearchGeneralOption()) {
            case TITLE:
                if (searchDto.getSearchFormatOption().equals(Medium.ANY)) {
                    foundAlbums = albumService.findByTitle(searchDto.getQuery());
                } else {
                    foundAlbums = albumService.findByTitleAndFormat(searchDto.getQuery(), searchDto.getSearchFormatOption());
                }
                break;
            case ARTIST:
                if (searchDto.getSearchFormatOption().equals(Medium.ANY)) {
                    foundAlbums = albumService.findByArtist(searchDto.getQuery());
                } else {
                    foundAlbums = albumService.findByArtistAndFormat(searchDto.getQuery(), searchDto.getSearchFormatOption());
                }
                break;
            case GENRE:
                if (searchDto.getSearchFormatOption().equals(Medium.ANY)) {
                    foundAlbums = albumService.findByGenre(searchDto.getQuery());
                } else {
                    foundAlbums = albumService.findByGenreAndFormat(searchDto.getQuery(), searchDto.getSearchFormatOption());
                }
                break;
            default:
                System.out.println("Something's not right...");
                break;
        }

        theModel.addAttribute("albums", foundAlbums);
        
        return "/albums/list-albums";
    }
}