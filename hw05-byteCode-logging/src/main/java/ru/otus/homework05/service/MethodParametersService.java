package ru.otus.homework05.service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MethodParametersService {

    public static boolean isEqualParametersInMethods(Method logMethod, Method method) {
        if (logMethod.getParameterCount() == method.getParameterCount()) {
            var paramsListOfLogMethod = getParamsListOfMethodInJoiningSting(logMethod);
            var paramsListOfMethod = getParamsListOfMethodInJoiningSting(method);
            return paramsListOfLogMethod.equals(paramsListOfMethod);
        }
        return false;
    }

    private static String getParamsListOfMethodInJoiningSting(Method method) {
        return Arrays.stream(method.getParameters())
                     .map(param -> param.getType().getName())
                     .collect(Collectors.joining(","));
    }
}
