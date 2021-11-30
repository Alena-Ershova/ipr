package uiTests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import models.Letter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.InboxPage;
import pages.MainPage;
import pages.NewLetterPage;
import utils.LoginData;

import static utils.LoginData.login;
import static utils.LoginData.password;
import static utils.TestUtils.createString;

@Epic("Тестирование почты mail.ru")
@Feature("Отправка письма")
public class SendLetterTest extends BaseTest{
    private static MainPage mainPage;
    private static InboxPage inboxPage;
    private static NewLetterPage letterPage;

    @BeforeAll
    public static void setUp(){
        mainPage = new MainPage();
        inboxPage = new InboxPage();
        letterPage = new NewLetterPage();
    }

    @DisplayName("Тест на отправку письма себе")
    @Test
    public void sendLetterTest() {
        mainPage.open();
        mainPage.login(login, password);
        Letter letter = new Letter(LoginData.login, createString(), createString(), null);
        mainPage.goToNewLetter();
        letterPage.sendLetterWithoutCopies(letter);
        inboxPage.clickOnLetterBySubject(letter.getSubject());
        inboxPage.checkLetterText(letter.getText());
        inboxPage.deleteOpenedLetter();
    }
}
