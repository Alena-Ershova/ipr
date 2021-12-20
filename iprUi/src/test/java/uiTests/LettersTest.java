package uiTests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import pages.InboxPage;
import pages.MainPage;

@Epic("Тестирование почты mail.ru")
@Feature("Получение писем")
public class LettersTest extends BaseTest{
    private static MainPage mainPage;
    private static InboxPage inboxPage;

    @BeforeAll
    public static void login(){
        mainPage = new MainPage();
        inboxPage = new InboxPage();
        mainPage.open();
        mainPage.login();
        inboxPage.loginSuccessful();
    }

    @DisplayName("Тест на получение входящих писем")
    @Test
    public void lettersTest() {
        inboxPage.printLetters();
    }
}
