package ru.otus.frontserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import ru.otus.frontserver.config.AppConfig;
import ru.otus.frontserver.model.dto.ClientDto;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {
    private final RequestService requestService;
    private final AppConfig config;

    public void saveClient(ClientDto clientDto) {
        requestService.post(getUri(config.getSave()), clientDto);
    }

    public Flux<ClientDto> findAll() {
        return requestService.get(getUri(config.getFind()));
    }

    private URI getUri(String path) {
        var url = config.getBasePath() + path;
        log.info("URL is: " + url);
        return UriComponentsBuilder.fromHttpUrl(url)
                                   .build()
                                   .toUri();
    }
}
