package com.shestee.albums.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class JsonUtilImpl implements JsonUtil {
    public String getAlbumJson(String releaseId) {
        RestTemplate rest = new RestTemplate();

        ResponseEntity<String> exchange = rest.exchange("https://api.discogs.com/releases/" + releaseId,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                String.class);

        return exchange.getBody();
    }
}
