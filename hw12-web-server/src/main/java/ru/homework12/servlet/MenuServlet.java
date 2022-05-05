package ru.homework12.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.homework12.services.TemplateProcessor;

import java.io.IOException;
import java.util.Collections;

public class MenuServlet extends HttpServlet {
    private static final String MENU = "menu.html";

    private final TemplateProcessor templateProcessor;

    public MenuServlet(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.getWriter().println(templateProcessor.getPage(MENU, Collections.emptyMap()));
    }
}
