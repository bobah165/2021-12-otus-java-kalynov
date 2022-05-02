package ru.homework12;

import ru.homework12.helpers.ApplicationRunner;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerWithBasicSecurityDemoApplication {

    public static void main(String[] args) throws Exception {
        var runner = new ApplicationRunner();
        runner.run();

    }
}
