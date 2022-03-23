package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.processor.ProcessorChangeFields;
import ru.otus.processor.ProcessorSecond;

import java.time.LocalDateTime;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
             Секунда должна определяьться во время выполнения.
             Тест - важная часть задания
             Обязательно посмотрите пример к паттерну Мементо!
       4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
          Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
          Для него уже есть тест, убедитесь, что тест проходит
     */

    public static void main(String[] args) {
        var processors = List.of(new ProcessorChangeFields(),
                new ProcessorSecond(HomeWork::getSeconds));

        var complexProcessor = new ComplexProcessor(processors, ex -> {
            System.out.print("Even second");
        });

        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        Message message = new Message.Builder(1)
                .field4("asdf")
                .field2("adf")
                .field11("1234")
                .field12("789")
                .build();

        var result = complexProcessor.handle(message);
        System.out.println();
        System.out.println("result:" + result);

        complexProcessor.removeListener(historyListener);


        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
    }

    private static int getSeconds() {
        LocalDateTime localDateTime = LocalDateTime.now();
        int hours = localDateTime.getHour();
        int minutes = localDateTime.getMinute();
        int seconds = localDateTime.getSecond();
        return hours * 3600 + minutes * 60 + seconds;
    }
}
