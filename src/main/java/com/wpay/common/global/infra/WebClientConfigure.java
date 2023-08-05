package com.wpay.common.global.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

/**
 * WebClient Bean 등록 후 사용 하세요
 */
@Log4j2
public class WebClientConfigure {

    @Getter private final TimeoutConfigureSetUp timeoutConfigureSetUp;
    @Getter private final ConnectionPoolConfigureSetUp connectionPoolConfigureSetUp;

    /**
     * <pre>
     *     서버 응답 데이터 임시 저장 버퍼 사이즈 설정:
     *     1 으로 설정하면 기본 1M(1024 * 1024)로 세팅 함.
     *     [참고]
     *       스프링 프레임워크 기본 크기는 256K 이다.
     *       -1 로 세팅 시 버퍼 사이즈는 무제한 이다.
     * </pre>
     */
    @Getter private final Integer maxMemorySizeByteCount;

    @Builder
    public WebClientConfigure(TimeoutConfigureSetUp timeoutConfigureSetUp, ConnectionPoolConfigureSetUp connectionPoolConfigureSetUp, Integer maxMemorySizeByteCount) {
        this.timeoutConfigureSetUp = timeoutConfigureSetUp;
        this.connectionPoolConfigureSetUp = connectionPoolConfigureSetUp;
        this.maxMemorySizeByteCount = (maxMemorySizeByteCount==1) ? 1024 * 1024 : maxMemorySizeByteCount;
    }

    public WebClient getWebClient() {
        return WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(this.getHttpClient()))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .exchangeStrategies(this.getExchangeStrategies())
                .filter(ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
                    log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
                    clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{} : {}", name, value)));
                    return Mono.just(clientRequest);
                }))
                .filter(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                    clientResponse.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> log.info("{} : {}", name, value)));
                    return Mono.just(clientResponse);
                }))
                .build();
    }

    protected HttpClient getHttpClient() {
        return HttpClient.create(this.getConnectionProvider())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000 * this.timeoutConfigureSetUp.getConnectionTimeoutSeconds())
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(this.timeoutConfigureSetUp.getReadTimeoutSeconds()))
                                .addHandlerLast(new WriteTimeoutHandler(this.timeoutConfigureSetUp.getWriteTimeoutSeconds())));
    }

    protected ConnectionProvider getConnectionProvider() {
        return ConnectionProvider.builder("http-pool")
                .maxConnections(this.connectionPoolConfigureSetUp.getMaxConnections())
                .pendingAcquireTimeout(Duration.ofMillis(this.connectionPoolConfigureSetUp.getPendingAcquireTimeoutMills()))
                .pendingAcquireMaxCount(this.connectionPoolConfigureSetUp.getPendingAcquireMaxCount())
                .maxIdleTime(Duration.ofMillis(this.connectionPoolConfigureSetUp.getMaxIdleTimeMillis()))
                .build();
    }

    protected ExchangeStrategies getExchangeStrategies() {
        final ObjectMapper OM = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());
        return ExchangeStrategies.builder().codecs(config -> {
            config.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(OM, MediaType.APPLICATION_JSON));
            config.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(OM, MediaType.APPLICATION_JSON));
            config.defaultCodecs().maxInMemorySize(this.maxMemorySizeByteCount); // max buffer 1MB 고정. default: 256 * 1024
        }).build();
    }

    /**
     * WebClient Timeout 세팅
     */
    @Getter
    @Value
    public static class TimeoutConfigureSetUp {
        /** 커넥션 타임아웃 max 10초 (10 이상 세팅할 경우 Build 시 10으로 강제 세팅 함.) */
        Integer connectionTimeoutSeconds;
        /** 서버 응답 대기 시간 max 10초 (10 이상 세팅할 경우 Build 시 10으로 강제 세팅 함.) */
        Integer readTimeoutSeconds;
        /** 서버로 데이터를 보내는 시간 타임아웃 max 10초 (10 이상 세팅할 경우 Build 시 10으로 강제 세팅 함.) */
        Integer writeTimeoutSeconds;
        @Builder
        public TimeoutConfigureSetUp(Integer connectionTimeoutSeconds, Integer readTimeoutSeconds, Integer writeTimeoutSeconds) {
            this.connectionTimeoutSeconds = (connectionTimeoutSeconds > 10) ? 10 : connectionTimeoutSeconds;
            this.readTimeoutSeconds = (readTimeoutSeconds > 10) ? 10 : readTimeoutSeconds;
            this.writeTimeoutSeconds = (writeTimeoutSeconds > 10) ? 10 : writeTimeoutSeconds;
        }
    }

    /**
     * WebClient 커넥션 풀 세팅
     */
    @Getter
    @Value
    @Builder
    @AllArgsConstructor
    public static class ConnectionPoolConfigureSetUp {
        /** connection pool의 갯수 */
        Integer maxConnections;
        /** 커넥션 풀에서 커넥션을 얻기 위해 기다리는 최대 시간 밀리초 [min:0 no wait]*/
        Long pendingAcquireTimeoutMills;
        /** 커넥션 풀에서 커넥션을 가져오는 시도 횟수 (-1: no limit) */
        Integer pendingAcquireMaxCount;
        /** 커넥션 풀에서 idle 상태의 커넥션을 유지하는 시간 밀리초 [예: 2000L]*/
        Long maxIdleTimeMillis;
    }
}
