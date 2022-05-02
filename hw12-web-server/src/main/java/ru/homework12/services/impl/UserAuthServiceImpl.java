package ru.homework12.services.impl;

import ru.homework12.services.DBServiceClient;
import ru.homework12.services.UserAuthService;

public class UserAuthServiceImpl implements UserAuthService {

    private final DBServiceClient client;

    public UserAuthServiceImpl(DBServiceClient client) {
        this.client = client;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return client.getByName(login)
                     .map(user -> user.getPassword().equals(password))
                     .orElse(false);
    }

}
