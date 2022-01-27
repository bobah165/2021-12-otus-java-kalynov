package ru.otus.homework03;

import ru.otus.homework03.service.TestRunnerService;


public class HomeWorkAnnotations {
    public static void main(String[] args) throws Exception {
        TestRunnerService.runTests(AnnotationTest.class);
    }
}
