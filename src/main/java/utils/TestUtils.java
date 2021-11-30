package utils;

import io.qameta.allure.Step;

import java.util.Random;

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
