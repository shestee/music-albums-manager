package com.shestee.albums.controller;

import com.shestee.albums.entity.Album;
import com.shestee.albums.entity.Song;
import com.shestee.albums.service.AlbumService;
import com.shestee.albums.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SongController {
    @Autowired
    SongService songService;

    @Autowired
    AlbumService albumService;

    @GetMapping("/deleteSong")
    public String removeSongById(@RequestParam("songId") int songId,
                                 RedirectAttributes redirectAttributes) {
        Album album = songService.getSongById(songId).getAlbum();
        int albumId = album.getId();

        songService.removeSongById(songId);

        redirectAttributes.addAttribute("albumId", albumId);

        return "redirect:/showSongs";
    }

    @GetMapping("/showFormForAddSong")
    public String showFormForAddSong(@RequestParam("albumId") int albumId, Model theModel) {
        Album album = albumService.findById(albumId);

        Song song = new Song();
        song.setAlbum(album);

        theModel.addAttribute("song", song);
        theModel.addAttribute("album", album);

        return "/albums/song-form";
    }

    @GetMapping ("/showFormForUpdateSong")
    public String showFormForUpdateSong(@RequestParam("songId") int songId, Model theModel) {
        Song song = songService.getSongById(songId);
        Album album = song.getAlbum();

        theModel.addAttribute("song", song);
        theModel.addAttribute("album", album);

        return "/albums/song-form";
    }

    @PostMapping("/addSong")
    public String addSong(@ModelAttribute("song") Song song,
                          RedirectAttributes redirectAttributes) {

        Album album = song.getAlbum();
        System.out.println("ALBUM ID: " + album.getId());

        redirectAttributes.addAttribute("albumId", album.getId());

        songService.addSong(song);

        return "redirect:/showSongs";
    }
}
