package uiTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import pages.BasicPage;
import pages.MainPage;

/**
 * Базовый класс для тестов
 */
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
}
