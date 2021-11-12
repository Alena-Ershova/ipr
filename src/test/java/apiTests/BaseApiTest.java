package apiTests;

import io.qameta.allure.Step;

import java.util.Random;

/**
 * Базовые методы для API тестов
 */
public class BaseApiTest {

    /**
     * Генератор строки
     * @return String
     */
    @Step("Создание названия")
    protected static String createName(){
        Random rand = new Random();
        return  "test" + rand.nextInt(100);
    }
}
