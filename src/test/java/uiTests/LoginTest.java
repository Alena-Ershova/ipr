package uiTests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import pages.InboxPage;
import pages.MainPage;

public class LoginTest extends BaseTest{
    private static MainPage mainPage;
    private static InboxPage inboxPage;

    @BeforeAll
    public static void setUp(){
        mainPage = new MainPage();
        inboxPage = new InboxPage();
    }

    @Test
    public void loginTest() {
        mainPage.open();
        mainPage.login();
        inboxPage.loginSuccessful();
    }
}
