package ru.otus.homework14.mapper;

import org.springframework.stereotype.Component;
import ru.otus.homework14.exception.AddressException;
import ru.otus.homework14.exception.NoSuchAddressException;
import ru.otus.homework14.model.Address;
import ru.otus.homework14.model.Client;
import ru.otus.homework14.model.Phone;
import ru.otus.homework14.model.dto.ClientDto;
import ru.otus.homework14.services.AddressService;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class ClientMapper {
    private final AddressService addressService;

    public ClientMapper(AddressService addressService) {
        this.addressService = addressService;
    }

    public ClientDto entityToDto(Client client) {
        var clientDto = new ClientDto();
        clientDto.setName(client.getName());
        clientDto.setStreet(getStreet(client));
        clientDto.setPhonesNumbers(getPhoneNumbers(client));
        return clientDto;
    }

    public Client dtoToEntity(ClientDto clientDto) {
        return new Client(clientDto.getName(), getAddressId(clientDto.getStreet()), new HashSet<>());
    }

    private Long getAddressId(String street) {
        return addressService.saveByName(street)
                             .map(Address::getId)
                             .orElseThrow(() -> new AddressException("No address was saved"));
    }

    private String getStreet(Client client) {
        var address = addressService.getAddressById(client.getAddressId());
        return address.map(Address::getStreet)
                      .orElseThrow(() -> new NoSuchAddressException("No address"));
    }

    private String getPhoneNumbers(Client client) {
        return client.getPhones()
                     .stream()
                     .map(Phone::getNumber)
                     .collect(Collectors.joining(" "));
    }
}
