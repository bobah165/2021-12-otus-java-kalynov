package ru.otus.dbserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.dbserver.mapper.ClientMapper;
import ru.otus.dbserver.model.Phone;
import ru.otus.dbserver.model.dto.ClientDto;
import ru.otus.dbserver.services.ClientService;
import ru.otus.dbserver.services.PhoneService;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper mapper;
    private final PhoneService phoneService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ClientDto> saveClient(@RequestBody ClientDto clientDto) {
        log.info("Get client: " + clientDto);
        var future = CompletableFuture
                .supplyAsync(() -> {
                    var client = mapper.dtoToEntity(clientDto);
                    var savedClient = clientService.saveClient(client);
                    savePhones(clientDto.getPhonesNumbers(), savedClient.getId());
                    return mapper.entityToDto(savedClient);
                });

        return Mono.fromFuture(future)
                   .doOnNext(value-> log.info("current value save is " + value));
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ClientDto> getAllClient() {
        log.info("find all clients");
        return Flux.fromIterable(clientService.findAll()
                                              .stream()
                                              .map(mapper::entityToDto)
                                              .collect(Collectors.toList()))
                   .doOnNext(value-> log.info("current value getAll is " + value));
    }

    private void savePhones(String phonesNumbers, long clientId) {
        var phones = phonesNumbers.split(",");
        Arrays.stream(phones).forEach(number -> {
            var phone = new Phone(number, clientId);
            phoneService.save(phone);
        });
    }
}
