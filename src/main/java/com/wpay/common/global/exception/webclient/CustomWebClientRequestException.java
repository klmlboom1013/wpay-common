package com.wpay.common.global.exception.webclient;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.net.URI;

@Getter
public class CustomWebClientRequestException extends WebClientRequestException {

    private final ClientResponse response;
    @Setter
    private Object mapper;

    public CustomWebClientRequestException(ClientResponse response, HttpMethod method, URI uri, Object mapper) {
        super(new RuntimeException(
                String.format("HTTP STATUS 4XX: Client Request Error. [status: %d] [message: %s] [header: %s] [responseBody: %s]",
                        response.statusCode(), HttpStatus.resolve(response.rawStatusCode()).getReasonPhrase(),
                        response.headers().asHttpHeaders(), response.bodyToMono(String.class))),
                method, uri, response.headers().asHttpHeaders());
        this.response=response;
        this.mapper = mapper;
    }
}
