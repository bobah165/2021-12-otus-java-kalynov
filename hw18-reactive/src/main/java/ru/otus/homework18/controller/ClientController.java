package ru.otus.homework18.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.homework18.mapper.ClientMapper;
import ru.otus.homework18.model.Phone;
import ru.otus.homework18.model.dto.ClientDto;
import ru.otus.homework18.services.ClientService;
import ru.otus.homework18.services.PhoneService;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class ClientController {
    private final ClientService clientService;
    private final PhoneService phoneService;
    private final ClientMapper mapper;

    public ClientController(ClientService clientService, PhoneService phoneService, ClientMapper mapper) {
        this.clientService = clientService;
        this.phoneService = phoneService;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public String getHomePage() {
        return "index.html";
    }

    @GetMapping("/menu")
    public String getMenuPage() {
        return "menu.html";
    }

    @GetMapping("/client")
    public String getSaveClientPage() {
        return "add_client.html";
    }

    @PostMapping("/save")
    public String saveClient(ClientDto clientDto) {
        var client = mapper.dtoToEntity(clientDto);
        var savedClient = clientService.saveClient(client);
        savePhones(clientDto.getPhonesNumbers(), savedClient.getId());
        return "redirect:/menu";
    }

    private void savePhones(String phonesNumbers, long clientId) {
        var phones = phonesNumbers.split(",");
        Arrays.stream(phones).forEach(number->{
            var phone = new Phone(number, clientId);
            phoneService.save(phone);
        });
    }

    @GetMapping("/list")
    public String getAllClient(Model model) {
        var clients = clientService.findAll()
                                   .stream()
                                   .map(mapper::entityToDto)
                                   .collect(Collectors.toList());
        model.addAttribute("clients", clients);
        return "list_client.html";
    }


}
