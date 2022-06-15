package ru.otus.frontserver.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.frontserver.model.dto.ClientDto;

import java.net.URI;


@Service
@RequiredArgsConstructor
public class RequestService {
    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);
    private final WebClient webClient;

    public Flux<ClientDto> get(URI uri) {
        return webClient.get()
                        .uri(uri)
                        .retrieve()
                        .bodyToFlux(ClientDto.class)
                        .doOnNext(value -> logger.info("value from front (get request) is " + value));
    }

    public void post(URI uri, ClientDto body) {
        webClient.post()
                 .uri(uri)
                 .contentType(MediaType.APPLICATION_JSON)
                 .body(Mono.just(body), ClientDto.class)
                 .retrieve()
                 .bodyToMono(ClientDto.class)
                 .doOnNext(clientDto -> logger.info("response " + clientDto));
    }
}
