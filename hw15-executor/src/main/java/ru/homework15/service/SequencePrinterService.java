package ru.homework15.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.homework15.model.ThreadNames;

import java.util.Comparator;
import java.util.List;


public class SequencePrinterService {
    private static final Logger logger = LoggerFactory.getLogger(SequencePrinterService.class);

    private String threadNames = ThreadNames.EVEN.name();

    private static final List<Integer> sequence = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    public void print() {
        try {
            printInDirectOrder();
            printInReverseOrder();
            printInDirectOrder();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void printInDirectOrder() {
        sequence.forEach(this::print);
    }

    private void printInReverseOrder() throws InterruptedException {
        sequence.stream()
                .sorted(Comparator.reverseOrder())
                .forEach(this::print);
    }

    private synchronized void print(int value) {
        while (threadNames.equals(Thread.currentThread().getName())) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        logger.info("current value is " + value);
        threadNames = threadNames.equals(ThreadNames.ODD.name()) ? ThreadNames.EVEN.name()
                                                                 : ThreadNames.ODD.name();
        notifyAll();
    }
}
