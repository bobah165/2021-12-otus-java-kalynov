package ru.otus.homework03.service;

import ru.otus.homework03.model.Linker;

import java.util.List;
import java.util.function.Predicate;

public class PrintResultService {

    public void print(List<Linker> testMethods) {
        var greenTestCount = getCount(testMethods, Linker::getTestMethodGreen);
        var failedTestCount = getCount(testMethods, linker -> !linker.getTestMethodGreen());

        System.out.println("\nCount of green tests is " + greenTestCount +
                    "\n" + "Count of failed tests is " + failedTestCount +
                    "\n" + "Total count of tests is " + testMethods.size());
    }

    private long getCount(List<Linker> testMethods, Predicate<Linker> isTestGreen) {
        return testMethods.stream()
                          .filter(isTestGreen)
                          .count();
    }
}
