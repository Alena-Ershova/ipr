package uiTests;

import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import pages.BasicPage;
import pages.MainPage;

import java.util.Random;

public class BaseTest {
    protected static BasicPage page;

    @BeforeAll
    public static void baseSetUp(){
        page = new MainPage();
    }
    @AfterAll
    public static void close(){
        page.close();
    }

    /**
     * Генератор строки
     * @return String
     */
    @Step("Создание строки")
    protected static String createString(){
        Random rand = new Random();
        return  "test" + rand.nextInt(100);
    }
}
