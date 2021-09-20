package uiTests;

import org.junit.Test;
import pages.InboxPage;
import pages.MainPage;

public class LoginTest extends BaseTest{
    MainPage mainPage = new MainPage();
    InboxPage inboxPage = new InboxPage();

    @Test
    public void loginTest() {
        mainPage.open();
        mainPage.login();
        inboxPage.loginSuccessful();
    }
}
