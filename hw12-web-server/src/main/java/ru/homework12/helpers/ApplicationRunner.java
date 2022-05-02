package ru.homework12.helpers;

import org.eclipse.jetty.security.LoginService;
import ru.homework12.config.DbConfig;
import ru.homework12.server.UsersWebServer;
import ru.homework12.server.UsersWebServerWithBasicSecurity;
import ru.homework12.services.DBServiceClient;
import ru.homework12.services.TemplateProcessor;
import ru.homework12.services.impl.AdminLoginService;
import ru.homework12.services.impl.TemplateProcessorImpl;

public class ApplicationRunner {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String USER_FILE = "users.csv";

    public void run() throws Exception {
        DbConfig config = new DbConfig();
        DBServiceClient serviceClient = config.getServiceClient();

        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        FileReader fileReader = new FileReader(USER_FILE);
        LoginService loginService = new AdminLoginService(fileReader);

        UsersWebServer usersWebServer = new UsersWebServerWithBasicSecurity(WEB_SERVER_PORT,
                loginService, serviceClient, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
