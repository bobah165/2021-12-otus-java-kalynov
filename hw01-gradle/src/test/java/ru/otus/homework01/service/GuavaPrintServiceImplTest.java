package ru.otus.homework01.service;

import org.junit.jupiter.api.Test;
import ru.otus.homework01.service.impl.GuavaPrintServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class GuavaPrintServiceImplTest {

    @Test
    void testGetList() {
        PrintService service = new GuavaPrintServiceImpl();
        List<String> expected = List.of("John", "Adam", "Jane");

        assertEquals(expected, service.getList());
    }
}