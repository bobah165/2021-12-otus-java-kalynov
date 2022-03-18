package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorSecond implements Processor {
    private DataTimeProvider dataTimeProvider;

    public ProcessorSecond(DataTimeProvider dataTimeProvider) {
        this.dataTimeProvider = dataTimeProvider;
    }

    @Override
    public Message process(Message message) {
        checkSecond();
        return message;
    }

    private void checkSecond() {
        long seconds = dataTimeProvider.getSeconds();
        if (seconds % 2 == 0) {
            throw new EvenSecondException();
        } else {
            System.out.print("Odd second");
        }
    }
}
