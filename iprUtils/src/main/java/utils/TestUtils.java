package utils;

import io.qameta.allure.Step;

import java.util.Random;

/**
 * Класс, содержащий утилитарные методы для тестов
 */
public class TestUtils {

    /**
     * Генератор строки
     *
     * @return String
     */
    @Step("Создание строки")
    public static String createString() {
        Random rand = new Random();
        return "test" + rand.nextInt(100);
    }
}
