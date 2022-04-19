package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import wrapper.DriverWrapper;

import static utils.TestDataStorage.getLogin;
import static utils.TestDataStorage.getPassword;

public class MainPage extends BasicPage {
    private String goToLoginButtonXpath = "//button[text()='Войти']";
    private String frameXpath = "//iframe[@class=\"ag-popup__frame__layout__iframe\"]";
    private String loginFieldXpath = "//input[@name=\"username\"]";
    private String domenFieldXpath = "//form[@data-testid=\"logged-out-form\"]//div[2]";
    private String passwordFieldXpath = "//*[@type=\"password\"]";
    private String enterPassButtonXpath = "//*[@data-test-id=\"next-button\"]";
    private String enterButtonXpath = "//*[@data-test-id=\"submit-button\"]";
    //адреса для отправки нового письма
    private String createNewLetter = "//span[text()='Написать письмо']/..";
    private String sentLetters = "//div[text()='Отправленные']/..";


    public MainPage() {
        super("Главная", "https://mail.ru");
    }

    @Step("Логин в почту")
    public void login() {
        clickOnElement(By.xpath(goToLoginButtonXpath));
        driver.switchTo(By.xpath(frameXpath));
        sendKeys(By.xpath(loginFieldXpath), getLogin("default"));
        clickOnElement(By.xpath(enterPassButtonXpath));
        sendKeys(By.xpath(passwordFieldXpath), getPassword("default"));
        clickOnElement(By.xpath(enterButtonXpath));
        driver.unswitch();
    }

    @Step("Переходим к отправке нового письма")
    public void goToNewLetter() {
        clickOnElement(By.xpath(createNewLetter));
    }

    @Step("Переходим к отправленным письмам")
    public void goToSentLetters() {
        clickOnElement(By.xpath(sentLetters));
    }
}
