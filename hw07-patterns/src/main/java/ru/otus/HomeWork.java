package ru.otus;

import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.processor.ProcessorChangeFields;

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
        Message message = new Message.Builder(1)
                .field4("asdf")
                .field2("adf")
                .field11("1234")
                .field12("789")
                .build();

        System.out.println(message);

        Processor processor = new ProcessorChangeFields();
        Message newMessage = processor.process(message);

        System.out.println(newMessage);


        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
    }
}
