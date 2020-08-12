package com.shestee.albums.parsers;

import com.shestee.albums.entity.Album;
import com.shestee.albums.entity.Song;
import com.shestee.albums.entity.enums.LengthType;
import com.shestee.albums.entity.enums.Medium;
import com.shestee.albums.utils.JsonUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AlbumJsonParser {
    @Autowired
    JsonUtil jsonUtil;

    public Album parseAlbumFromAlbumJson(String discogsReleaseId) {
        
        Album parsedAlbum = new Album();
        
        try {
            String response = jsonUtil.getAlbumJson(discogsReleaseId);

            JSONObject jsonObject = new JSONObject(response);
            JSONArray labels = jsonObject.getJSONArray("labels");
            JSONArray genres = jsonObject.getJSONArray("genres");
            JSONArray formats = jsonObject.getJSONArray("formats");
            parsedAlbum.setArtist(jsonObject.getString("artists_sort"));
            parsedAlbum.setTitle(jsonObject.getString("title"));
            //parsedAlbum.setCatalogueNumber(labels.getJSONObject(0).getString("catno"));
            String catNo = "";
            for (int i=0; i<labels.length(); i++) {
                if (i == labels.length()-1) {
                    catNo += labels.getJSONObject(i).getString("catno");
                } else {
                    catNo += labels.getJSONObject(i).getString("catno") + ", ";
                }
            }
            parsedAlbum.setCatalogueNumber(catNo);

            String genreStr = "";
            for (int i = 0; i < genres.length() ; i++) {
                if (i == genres.length()-1) {
                    genreStr += genres.getString(i);
                } else {
                    genreStr += genres.getString(i) + ", ";
                }
            }

            parsedAlbum.setGenre(genreStr);
            parsedAlbum.setLabel(labels.getJSONObject(0).getString("name"));
            String lenghtype = formats.getJSONObject(0).getJSONArray("descriptions").get(0).toString();
            switch (lenghtype) {
                case "LP":
                case "Album":
                    parsedAlbum.setLengthType(LengthType.LP);
                    break;
                case "Single":
                    parsedAlbum.setLengthType(LengthType.SINGLE);
                    break;
                case "EP":
                    parsedAlbum.setLengthType(LengthType.EP);
                    break;
                default:
                    parsedAlbum.setLengthType(LengthType.OTHER);
                    break;
            }
            parsedAlbum.setLengthType(LengthType.LP);
            String medium = formats.getJSONObject(0).getString("name");
            switch (medium) {
                case "Vinyl":
                    parsedAlbum.setMedium(Medium.VINYL);
                    break;
                case "Cassette":
                    parsedAlbum.setMedium(Medium.CASSETTE);
                    break;
                case "CD":
                    parsedAlbum.setMedium(Medium.CD);
                    break;
                default:
                    parsedAlbum.setMedium(Medium.OTHER);
                    break;

            }
            parsedAlbum.setYear(jsonObject.getInt("year"));
        } catch (org.json.JSONException exc) {
            System.out.println("Something went wrong: " + exc.getMessage());
        }

        return parsedAlbum;
    }

    public static List<Song> parseSongsFromAlbumJson(String response) {
        List<Song> parsedSongs = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(response);
        JSONArray songs = jsonObject.getJSONArray("tracklist");
        for (int i = 0; i < songs.length(); i++) {
            JSONObject songToParse = songs.getJSONObject(i);
            String songPosition = songToParse.getString("position");
            String songTitle = songToParse.getString("title");
            parsedSongs.add(new Song(songPosition, songTitle));
        }

        return parsedSongs;
    }
}

