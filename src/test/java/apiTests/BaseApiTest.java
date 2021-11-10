package apiTests;

import java.util.Random;

/**
 * Базовые методы для API тестов
 */
public class BaseApiTest {

    /**
     * Генератор строки
     * @return String
     */
    protected static String createName(){
        Random rand = new Random();
        return  "test" + rand.nextInt(100);
    }
}
