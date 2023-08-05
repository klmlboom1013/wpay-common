package com.wpay.common.global.infra.sample;

import com.wpay.common.global.infra.WebClientConfigure;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientSampleBean {
    @Bean("sampleWebClientConfigure")
    public WebClientConfigure sampleWebClientConfigure() {
        return WebClientConfigure.builder()
                .timeoutConfigureSetUp(WebClientConfigure.TimeoutConfigureSetUp.builder()
                        .connectionTimeoutSeconds(3)
                        .readTimeoutSeconds(3)
                        .writeTimeoutSeconds(3)
                        .build())
                .connectionPoolConfigureSetUp(WebClientConfigure.ConnectionPoolConfigureSetUp.builder()
                        .maxConnections(1)
                        .pendingAcquireMaxCount(1)
                        .pendingAcquireTimeoutMills(0L)
                        .maxIdleTimeMillis(2000L)
                        .build())
                .maxMemorySizeByteCount(1)
                .build();
    }
}
