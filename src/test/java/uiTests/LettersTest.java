package uiTests;

import org.junit.Before;
import org.junit.Test;
import pages.InboxPage;
import pages.MainPage;

public class LettersTest extends BaseTest{
    MainPage mainPage = new MainPage();
    InboxPage inboxPage = new InboxPage();

    @Before
    public void login(){
        mainPage.open();
        mainPage.login();
        inboxPage.loginSuccessful();
    }
    @Test
    public void lettersTest() {
        inboxPage.printLetters();
    }
}
