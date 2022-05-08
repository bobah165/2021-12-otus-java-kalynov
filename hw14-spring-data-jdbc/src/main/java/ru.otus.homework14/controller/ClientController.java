package ru.otus.homework14.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.homework14.model.Client;
import ru.otus.homework14.services.ClientService;

@Controller
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
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
    public String saveClient(Client client) {
        clientService.saveClient(client);
        return "redirect:/menu";
    }

    @GetMapping("/list")
    public String  getAllClient(Model model) {
        var clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "list_client.html";
    }


}
