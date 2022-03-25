package ru.otus.dataprocessor;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import ru.otus.model.Measurement;

import javax.json.Json;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        List<Measurement> measurements = new ArrayList<>();
        try {
            var jsonString = readFileToString();
            measurements = parseStringJson(jsonString);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }

        //читает файл, парсит и возвращает результат
        return measurements;
    }

    private List<Measurement> parseStringJson(String string) throws JsonProcessingException {
        Gson gson = new Gson();
        return Arrays.asList(gson.fromJson(string, Measurement[].class));
    }

    private String readFileToString() throws IOException {
        String string;
        try (var stream = Json.createReader(ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName))) {
            string = stream.read().toString();
        }
        return string;
    }
}
