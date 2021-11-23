package uiTests;

import io.qameta.allure.Epic;
import models.Letter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.InboxPage;
import pages.MainPage;
import utils.Data;

@Epic("Ui тесты")
public class SendLetterTest extends BaseTest{
    private static MainPage mainPage;
    private static InboxPage inboxPage;
    private Letter letter;

    @BeforeAll
    public static void setUp(){
        mainPage = new MainPage();
        inboxPage = new InboxPage();
    }

    @DisplayName("Тест на отправку письма себе")
    @Test
    public void sendLetterTest() {
        letter = new Letter(Data.login, createString(), createString(), null);
        mainPage.sendLetterWithoutCopies(letter);
        inboxPage.clickOnLetterBySubject(letter.getSubject());
        inboxPage.checkLetterText(letter.getText());
        inboxPage.deleteOpenedLetter();
    }
}
