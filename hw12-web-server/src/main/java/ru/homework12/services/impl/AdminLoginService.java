package ru.homework12.services.impl;

import org.eclipse.jetty.security.AbstractLoginService;
import org.eclipse.jetty.security.RolePrincipal;
import org.eclipse.jetty.security.UserPrincipal;
import org.eclipse.jetty.util.security.Password;
import ru.homework12.exception.ReadDataFromFileException;
import ru.homework12.helpers.FileReader;

import java.util.List;

public class AdminLoginService extends AbstractLoginService {

    private final FileReader fileReader;

    public AdminLoginService(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    @Override
    protected List<RolePrincipal> loadRoleInfo(UserPrincipal userPrincipal) {
        return List.of(new RolePrincipal("admin"));
    }

    @Override
    protected UserPrincipal loadUserInfo(String login) {
        try {
            var user = fileReader.readDataFromFile();
            return new UserPrincipal(user.getName(), new Password(user.getPassword()));
        } catch (ReadDataFromFileException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
