package ru.homework12.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.homework12.model.Address;
import ru.homework12.model.Client;
import ru.homework12.model.Phone;
import ru.homework12.services.DBServiceClient;
import ru.homework12.services.TemplateProcessor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class ClientsServlet extends HttpServlet {

    private static final String CLIENT_PAGE_TEMPLATE = "add_client.html";

    private final DBServiceClient serviceClient;
    private final TemplateProcessor templateProcessor;

    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient serviceClient) {
        this.templateProcessor = templateProcessor;
        this.serviceClient = serviceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENT_PAGE_TEMPLATE, Collections.emptyMap()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var name = req.getParameter("newName");
        var pass = req.getParameter("newPass");
        var street = req.getParameter("street");
        var phones = req.getParameterValues("phone");

        saveClient(name, pass, street, phones);

        resp.sendRedirect("/menu");
    }

    private void saveClient(String name, String pass, String street, String[] phones) {
        var address = new Address(null, street);
        var phoneList = getPhoneList(phones);
        var client = new Client(null, name, pass, address, phoneList);
        serviceClient.saveClient(client);
    }

    private List<Phone> getPhoneList(String[] phones) {
        return Arrays.stream(phones)
                              .filter(phone -> !phone.equals(""))
                              .map(phone -> new Phone(null, phone))
                              .collect(Collectors.toList());
    }
}
