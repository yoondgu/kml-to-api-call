package com.example.demo;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.example.geographicfileparser.PinCreateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class WebController {

    @PostMapping("/pins")
    public void insert() throws JsonProcessingException {
        URI url = UriComponentsBuilder.fromHttpUrl("https://mapbefine.kro.kr/api/pins")
                .build()
                .toUri();
        ObjectMapper objectMapper = new ObjectMapper();

        WebClient webClient = WebClient.builder().build();

        System.out.println(url);
        String pinJson = objectMapper.writeValueAsString(new PinCreateRequest(551730L, "붕어빵테스트", "붕어빵테스트 description", "붕어빵 테스트 address", "temp", 35.172544, 129.136106));
        HttpStatusCode statusCode = webClient.post()
                .uri(url)
                /// TODO: 2023/09/28  토큰 직접 입력하기
                .header(AUTHORIZATION, "token")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                /// TODO: 2023/09/28 multipart 넣는 다른 방식 찾아봐야 할듯
                .body(BodyInserters.fromMultipartData("request", pinJson))
                .exchangeToMono(response -> {
                    HttpStatusCode httpStatusCode = response.statusCode();
                    try {
                        System.out.println(httpStatusCode.value());
                        return response.bodyToMono(HttpStatus.class).thenReturn(httpStatusCode);
                    } catch (Exception exception) {
                        System.out.println(httpStatusCode.value());
                        throw new IllegalStateException(exception);
                    }
                })
                .block();

        System.out.println("done");
    }
}
