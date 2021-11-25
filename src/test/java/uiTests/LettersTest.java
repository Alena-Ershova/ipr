package uiTests;

import io.qameta.allure.Epic;
import org.junit.jupiter.api.*;
import pages.InboxPage;
import pages.MainPage;
import utils.LoginData;

@Epic("Ui тесты")
public class LettersTest extends BaseTest{
    private static MainPage mainPage;
    private static InboxPage inboxPage;

    @BeforeAll
    public static void login(){
        mainPage = new MainPage();
        inboxPage = new InboxPage();
        mainPage.open();
        mainPage.login(LoginData.login, LoginData.password);
        inboxPage.loginSuccessful();
    }

    @DisplayName("Тест на получение входящих писем")
    @Test
    public void lettersTest() {
        inboxPage.printLetters();
    }
}
