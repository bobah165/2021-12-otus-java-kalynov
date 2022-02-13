package ru.otus.homework05.service;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArgsService {

    public static String getArgs(Object[] args) {
        return Optional.ofNullable(args)
                       .map(ArgsService::mapObjectArrayToJoiningString)
                       .orElse("0");
    }

    private static String mapObjectArrayToJoiningString(Object[] args) {
        return Arrays.stream(args)
                     .map(Object::toString)
                     .collect(Collectors.joining(", "));
    }
}
