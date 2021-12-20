package uiTests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import pages.InboxPage;
import pages.MainPage;

@Epic("Тестирование почты mail.ru")
@Feature("Авторизация в почту")
public class LoginTest extends BaseTest{
    private static MainPage mainPage;
    private static InboxPage inboxPage;

    @BeforeAll
    public static void setUp(){
        mainPage = new MainPage();
        inboxPage = new InboxPage();
    }

    @DisplayName("Тест на логин")
    @Test
    public void loginTest() {
        mainPage.open();
        mainPage.login();
        inboxPage.loginSuccessful();
    }
}
