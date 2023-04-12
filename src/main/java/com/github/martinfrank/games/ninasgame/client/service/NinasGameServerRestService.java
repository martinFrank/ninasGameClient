package com.github.martinfrank.games.ninasgame.client.service;

import com.github.martinfrank.ninasgame.model.map.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NinasGameServerRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NinasGameServerRestService.class);

    private final WebClient webClient;

    public NinasGameServerRestService(){
        webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
//                .defaultCookie("cookie-name", "cookie-value")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    public Map loadMap() {
        Map retrievedMap = webClient.get()
                .uri("/maps/generate")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(Mono.just(empl), Map.class) //bei post
                .retrieve()
                .bodyToMono(Map.class).block();
        System.out.println("map: "+retrievedMap.getWidth()+"/"+retrievedMap.getHeight());
        return retrievedMap;
    }


}
