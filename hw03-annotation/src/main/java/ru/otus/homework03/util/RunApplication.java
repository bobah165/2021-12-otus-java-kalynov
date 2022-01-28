package ru.otus.homework03.util;

import ru.otus.homework03.service.CreateInstanceService;
import ru.otus.homework03.service.InvokeService;
import ru.otus.homework03.service.LinkerService;
import ru.otus.homework03.service.PrintResultService;
import ru.otus.homework03.service.TestRunnerService;

public class RunApplication {

    public static <T> void run(Class<T> clazz) {
        CreateInstanceService createInstanceService = new CreateInstanceService();
        LinkerService method = new LinkerService();
        PrintResultService printResultService = new PrintResultService();
        InvokeService invokeService = new InvokeService(createInstanceService, method, printResultService);
        TestRunnerService runnerService = new TestRunnerService(invokeService);

        runnerService.runTests(clazz);
    }
}
