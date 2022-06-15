package ru.otus.frontserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
    private final AppConfig config;

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder()
                        .build();
    }
}
