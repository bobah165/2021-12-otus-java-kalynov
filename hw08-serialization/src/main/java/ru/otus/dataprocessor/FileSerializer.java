package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {
    public final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        var jsonString = convertMapToString(data);
        saveStringToFile(jsonString);
        //формирует результирующий json и сохраняет его в файл
    }

    private String convertMapToString(Map<String, Double> data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    private void saveStringToFile(String jsonString) {
        try (var writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(jsonString);
        } catch (IOException exception) {
            throw new FileProcessException(exception);
        }
    }
}
