package com.wpay.common.global.infra;

import com.wpay.common.global.exception.webclient.CustomWebClientRequestException;
import com.wpay.common.global.exception.webclient.CustomWebClientResponseException;
import com.wpay.common.global.exception.webclient.CustomWebClientTimeoutException;
import io.netty.channel.ConnectTimeoutException;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.WriteTimeoutException;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

import javax.ws.rs.core.MediaType;
import java.net.URI;

@Log4j2
@Component
public class WebClientUseTemplate {

    public String httpGetSendRetrieveToAppForm (@NonNull WebClient webClient,
                                                @NonNull URI connUrl) {
        try {
            return webClient.get().uri(connUrl)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, response ->
                            Mono.error(new CustomWebClientRequestException(response, HttpMethod.POST, connUrl, null)))
                    .onStatus(HttpStatus::is5xxServerError, response ->
                            Mono.error(new CustomWebClientResponseException(response, HttpMethod.POST, connUrl, null)))
                    .bodyToMono(String.class) // 응답 받을 객체 String.class 설정.
                    .block();
        } catch (WebClientRequestException ex) {
            if ((ex.getCause() instanceof ConnectTimeoutException)
                    || (ex.getCause() instanceof ReadTimeoutException)
                    || (ex.getCause() instanceof WriteTimeoutException)) {
                throw new CustomWebClientTimeoutException(connUrl, ex.getCause());
            }
            throw ex;
        }
    }
}
