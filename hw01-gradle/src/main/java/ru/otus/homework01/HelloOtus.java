package ru.otus.homework01;

import ru.otus.homework01.service.PrintService;
import ru.otus.homework01.service.impl.GuavaPrintServiceImpl;


public class HelloOtus {
    public static void main(String[] args) {
        PrintService service = new GuavaPrintServiceImpl();
        service.getList()
               .forEach(System.out::println);
    }
}
