package ru.homework12.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.homework12.services.DBServiceClient;
import ru.homework12.services.TemplateProcessor;

import java.io.IOException;
import java.util.Map;

public class ClientListServlet extends HttpServlet {
    private static final String LIST_CLIENT_TEMPLATE = "list_client.html";

    private final TemplateProcessor templateProcessor;
    private final DBServiceClient serviceClient;

    public ClientListServlet(TemplateProcessor templateProcessor, DBServiceClient serviceClient) {
        this.templateProcessor = templateProcessor;
        this.serviceClient = serviceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var clients = serviceClient.findAll();

        resp.setContentType("text/html");
        resp.getWriter().println(templateProcessor.getPage(LIST_CLIENT_TEMPLATE, Map.of("clients", clients)));
    }
}
