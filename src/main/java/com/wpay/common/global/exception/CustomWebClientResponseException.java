package com.wpay.common.global.exception;

import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Getter
public class CustomWebClientResponseException extends WebClientResponseException {

    private final ClientResponse response;
    private final URI uri;
    private final Object mapper;
    private final String message;

    public CustomWebClientResponseException(ClientResponse response, HttpMethod method, URI uri, Object mapper) {
        super(response.statusCode().value(),
                HttpStatus.resolve(response.rawStatusCode()).getReasonPhrase(),
                response.headers().asHttpHeaders(),
                response.bodyToMono(String.class).block().getBytes(),
                StandardCharsets.UTF_8);
        this.response=response;
        this.mapper=mapper;
        this.uri=uri;
        this.message = String.format("HTTP STATUS 5XX: Server Response Error. [status: %d] [message: %s] [header: %s] [responseBody: %s]",
                response.statusCode(), HttpStatus.resolve(response.rawStatusCode()).getReasonPhrase(),
                response.headers().asHttpHeaders(), response.bodyToMono(String.class));
    }
}
