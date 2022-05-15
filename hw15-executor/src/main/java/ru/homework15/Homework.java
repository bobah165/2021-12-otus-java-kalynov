package ru.homework15;

import ru.homework15.model.ThreadNames;
import ru.homework15.service.SequencePrinterService;


public class Homework {

    public static void main(String[] args) {
        SequencePrinterService sequencePrinterService = new SequencePrinterService();

        var thread1 = new Thread(sequencePrinterService::print);
        thread1.setName(ThreadNames.ODD.name());

        var thread2 = new Thread(sequencePrinterService::print);
        thread2.setName(ThreadNames.EVEN.name());

        thread1.start();
        thread2.start();

    }
}

