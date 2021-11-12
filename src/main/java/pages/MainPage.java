package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class MainPage extends BasicPage{
    private String loginFieldXpath = "//input[@name=\"login\"]";
    private String domenFieldXpath = "//form[@data-testid=\"logged-out-form\"]//div[2]";
    private String passwordFieldXpath = "//*[@type=\"password\"]";
    private String enterPassButtonXpath = "//*[@data-testid=\"enter-password\"]";
    private String enterButtonXpath = "//*[@data-testid=\"login-to-mail\"]";
    //данные
    private String login = "enotov.enot@internet.ru";
    private String password = "pass51423";

    public MainPage() {
        super("Главная", "https://mail.ru");
    }

    @Step("Логин в почту")
    public void login(){
        sendKeys(By.xpath(loginFieldXpath),login);
        clickOnElement(By.xpath(enterPassButtonXpath));
        sendKeys(By.xpath(passwordFieldXpath),password);
        clickOnElement(By.xpath(enterButtonXpath));
    }
}
