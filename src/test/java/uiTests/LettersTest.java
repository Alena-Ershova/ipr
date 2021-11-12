package uiTests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import pages.InboxPage;
import pages.MainPage;

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

    @Test
    public void lettersTest() {
        inboxPage.printLetters();
    }
}
