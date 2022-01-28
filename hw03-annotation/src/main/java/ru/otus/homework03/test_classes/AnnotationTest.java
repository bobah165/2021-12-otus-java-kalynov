package ru.otus.homework03.test_classes;

import ru.otus.homework03.annotation.After;
import ru.otus.homework03.annotation.Before;
import ru.otus.homework03.annotation.Test;

public class AnnotationTest {

    @Before
    public void setUp() {
        System.out.print("\n@Before ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }

    @Test
    public void anyTest1() {
        System.out.print("@Test: anyTest1. ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }

    @Test
    public void anyTest2() {
        throw new RuntimeException();
    }

    @Test
    public void anyTest3() {
        System.out.print("@Test: anyTest3. ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }

    @After
    public void tearDown() {
        System.out.print("@After ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }
}
