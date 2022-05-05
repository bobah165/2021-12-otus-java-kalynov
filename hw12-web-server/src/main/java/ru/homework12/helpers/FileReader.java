package ru.homework12.helpers;


import org.apache.commons.io.IOUtils;
import ru.homework12.exception.ReadDataFromFileException;
import ru.homework12.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;


public class FileReader {

    private final String fileName;

    public FileReader(String fileName) {
        this.fileName = fileName;
    }

    public User readDataFromFile() throws ReadDataFromFileException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            String dataFromFile = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
            return getUser(dataFromFile);
        } catch (IOException e) {
            throw new ReadDataFromFileException("Can't read data from file " + fileName);
        }
    }

    private User getUser(String dataFromFile) {
        System.out.println();
        List<String> usersData = Arrays.asList(dataFromFile.split("\n"));
        var userData = usersData.get(0).split(";");
        return new User(userData[0].trim(), userData[1].trim());
    }
}


