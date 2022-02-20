package ru.otus.homework06.service;

import ru.otus.homework06.model.Atm;

public class AtmService {
    private static Atm atm;

    private AtmService() {}

    public static Atm getAtmInstance() {
        if (atm==null) {
            atm = new Atm();
        }
        return atm;
    }
}
