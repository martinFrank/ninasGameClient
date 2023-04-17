package com.github.martinfrank.games.ninasgame.client.service;

import com.github.martinfrank.games.ninasgame.client.util.TilesetImageWriter;
import com.github.martinfrank.ninasgame.model.map.Map;
import com.github.martinfrank.ninasgame.model.tiledmap.TilesetImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Component
public class NinasGameServerRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NinasGameServerRestService.class);

    private final WebClient webClient;

    public NinasGameServerRestService(){
//        webClient = WebClient.builder()
//                .baseUrl("http://localhost:8080")
////                .defaultCookie("cookie-name", "cookie-value")
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .build();
        webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
//                .defaultCookie("cookie-name", "cookie-value")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build();

//        webClient.mutate()
//                .codecs(configurer -> configurer
//                        .defaultCodecs()
//                        .maxInMemorySize(16 * 1024 * 1024));
//                .build().get()
//                .uri("/u/r/l")
//                .exchange()
//                .expectStatus()
//                .isOk();
    }
    public Map loadMap() {
        TilesetImage retrievedMap = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tiledmaps/tileset-images")
                        .queryParam("id", 102L)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, createSecurityHeaders("test", "test") )
//                .body(Mono.just(empl), Map.class) //bei post
                .retrieve()
                .bodyToMono(TilesetImage.class).block();
        System.out.println("TilesetImage: "+retrievedMap.getFilename() );
//        System.out.println("TilesetImage: "+retrievedMap.getImageContentAsBase64String() );
        TilesetImageWriter.saveTilesetImage(retrievedMap.getFilename(), retrievedMap.getImageContentAsBase64String());
        return null;
    }

    public Map loadMapBackup() {
        Map retrievedMap = webClient.get()
                .uri("/maps/generate")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, createSecurityHeaders("test", "test") )
//                .body(Mono.just(empl), Map.class) //bei post
                .retrieve()
                .bodyToMono(Map.class).block();
        System.out.println("map: "+retrievedMap.getWidth()+"/"+retrievedMap.getHeight());
        return retrievedMap;
    }

    String createSecurityHeaders(String username, String password){

        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes() );
        String authHeader = "Basic " + new String( encodedAuth );
        return authHeader;
    }
}
