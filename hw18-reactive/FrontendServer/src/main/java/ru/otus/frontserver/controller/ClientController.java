package ru.otus.frontserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.frontserver.model.dto.ClientDto;
import ru.otus.frontserver.service.ClientService;

@Controller
@RequiredArgsConstructor
public class ClientController {
 private final ClientService clientService;

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
        clientService.saveClient(clientDto);
        return "redirect:/menu";
    }

    @GetMapping("/list")
    public String getAllClient(Model model) {
        var clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "list_client.html";
    }
}
