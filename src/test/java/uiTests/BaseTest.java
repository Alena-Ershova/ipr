package uiTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import pages.MainPage;

public class BaseTest {

    @AfterAll
    public static void close(){
        MainPage page = new MainPage();
        page.close();
    }
}
