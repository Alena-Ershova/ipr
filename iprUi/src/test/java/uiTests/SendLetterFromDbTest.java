package uiTests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import models.DBLetter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.InboxPage;
import pages.MainPage;
import pages.NewLetterPage;

import static dbintegration.DbDataParser.getLetter;


@Epic("Тестирование почты mail.ru")
@Feature("Отправка письма из базы данных")
public class SendLetterFromDbTest extends BaseTest{
    private static MainPage mainPage;
    private static InboxPage inboxPage;
    private static NewLetterPage letterPage;

    @BeforeAll
    public static void setUp(){
        mainPage = new MainPage();
        inboxPage = new InboxPage();
        letterPage = new NewLetterPage();
    }

    @DisplayName("Тест на отправку письма из базы данных")
    @Test
    public void sendLetterTest() {
        mainPage.open();
        mainPage.login();
        mainPage.goToNewLetter();
        DBLetter letter = getLetter();
        letterPage.sendLetterWithoutCopies(letter);
        inboxPage.clickOnLetterBySubject(letter.getSubject());
        inboxPage.checkLetterText(letter.getContent());
        inboxPage.deleteOpenedLetter();
    }
}
