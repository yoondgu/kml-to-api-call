package com.example.demo;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@RestController
public class WebController {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private final PinService pinService;
    private String accessToken;

    public WebController(
            @Value("${authorization.access-token}")
            String accessToken,
            PinService pinService
    ) {
        this.accessToken = accessToken;
        this.pinService = pinService;
    }

    @PostMapping("/pins")
    public void insert() throws Exception {
        URI url = UriComponentsBuilder.fromHttpUrl("https://mapbefine.kro.kr/api/pins")
                .build()
                .toUri();
        WebClient webClient = WebClient.builder().build();

        List<PinCreateRequest> pins = pinService.parsePins("붕어빵.txt");
        pins.forEach(pin -> {
            try {
                sendPost(url, webClient, pin);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void sendPost(URI url, WebClient webClient, PinCreateRequest pinCreateRequest) throws JsonProcessingException {
        MultiValueMap<String, HttpEntity<?>> multipartBody = buildMultipartBody(pinCreateRequest);
        System.out.println(multipartBody.get("request"));

        try {
            Mono<String> responseBody = webClient.post()
                    .uri(url)
                    .header(AUTHORIZATION, "Bearer " + accessToken)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(multipartBody))
                    .retrieve()
                    .onStatus(
                            HttpStatus.INTERNAL_SERVER_ERROR::equals,
                            response -> response.bodyToMono(String.class).map(Exception::new))
                    .bodyToMono(String.class);
            System.out.println(responseBody.block());
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        System.out.println("done");
    }

    private MultiValueMap<String, HttpEntity<?>> buildMultipartBody(PinCreateRequest pinCreateRequest) throws JsonProcessingException {
        String pinJson = objectMapper.writeValueAsString(pinCreateRequest);
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("request", pinJson, MediaType.APPLICATION_JSON);
        return multipartBodyBuilder.build();
    }
}
