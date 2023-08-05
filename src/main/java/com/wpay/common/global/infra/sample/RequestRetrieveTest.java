package com.wpay.common.global.infra.sample;

import com.wpay.common.global.exception.BadWebClientRequestException;
import com.wpay.common.global.infra.WebClientConfigure;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class RequestRetrieveTest {

    @Qualifier(value = "sampleWebClientConfigure")
    private final WebClientConfigure webClientConfigure;

    private Mono<ResponseEntity<ResResult>> retrievePostForMono(String uri, MultiValueMap<String, String> body) throws WebClientResponseException {
        return webClientConfigure.getWebClient()
                .post()
                .uri(uri)
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response ->
                        Mono.error(
                                new BadWebClientRequestException(
                                        response.rawStatusCode(),
                                        String.format("4xx 외부 요청 오류. statusCode: %s, response: %s, header: %s",
                                                response.rawStatusCode(),
                                                response.bodyToMono(String.class),
                                                response.headers().asHttpHeaders())
                                )
                        )
                )
                .onStatus(HttpStatus::is5xxServerError, response ->
                        Mono.error(
                                new WebClientResponseException(
                                        response.rawStatusCode(),
                                        String.format("5xx 외부 시스템 오류. %s", response.bodyToMono(String.class)),
                                        response.headers().asHttpHeaders(), null, null
                                )
                        )
                ).toEntity(ResResult.class);
    }
}
