package com.github.martinfrank.games.ninasgame.client.service;

import com.github.martinfrank.ninasgame.model.account.AccountDetails;
import com.github.martinfrank.ninasgame.model.account.LoginDetails;
import com.github.martinfrank.ninasgame.model.file.File;
import com.github.martinfrank.ninasgame.model.map.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Component
public class NinasGameServerRestServiceImpl implements NinasGameServerRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NinasGameServerRestServiceImpl.class);

    private final WebClient webClient;

    public NinasGameServerRestServiceImpl() {
        webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
//                .defaultCookie("cookie-name", "cookie-value")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .codecs(codecConfigurer -> codecConfigurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build();
    }

    @Override
    public AccountDetails loadAccountDetails(String user, String pass) {
        LOGGER.debug("load account details");
        try {
            LoginDetails loginDetails = new LoginDetails(user, pass);
            AccountDetails accountDetails = webClient.post()
                    .uri("/accounts")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, createSecurityHeaders("test", "test"))
                    .body(Mono.just(loginDetails), LoginDetails.class) //bei post
                    .retrieve()
                    .bodyToMono(AccountDetails.class).block();
            return accountDetails;
        } catch (WebClientException e) {
            LOGGER.error("error loading account details: {}", e.getMessage());
            return null;
        }

    }

    @Override
    public Map loadMap(String mapName) {
        LOGGER.debug("load map {}", mapName);
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/maps")
                            .queryParam("name", mapName)
                            .build())
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, createSecurityHeaders("test", "test"))
//                .body(Mono.just(empl), Map.class) //bei post
                    .retrieve()
                    .bodyToMono(Map.class).block();
        } catch (WebClientException e) {
            LOGGER.error("error loading map: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public File loadFile(String filename) {
        LOGGER.debug("load file with name {}",filename);
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/files")
                            .queryParam("filename", filename)
                            .build())
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, createSecurityHeaders("test", "test"))
//                    .body(Mono.just(loginDetails), LoginDetails.class) //bei post
                    .retrieve()
                    .bodyToMono(File.class).block();
        } catch (WebClientException e) {
            LOGGER.error("error loading file: {}", e.getMessage());
            return null;
        }
    }



    String createSecurityHeaders(String username, String password) {

        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }


}
