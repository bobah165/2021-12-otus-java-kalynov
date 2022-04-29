package ru.homework12.services;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
