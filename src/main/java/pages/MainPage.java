package pages;

import io.qameta.allure.Step;
import models.Letter;
import org.openqa.selenium.By;

import static utils.LoginData.login;
import static utils.LoginData.password;

public class MainPage extends BasicPage{
    private String loginFieldXpath = "//input[@name=\"login\"]";
    private String domenFieldXpath = "//form[@data-testid=\"logged-out-form\"]//div[2]";
    private String passwordFieldXpath = "//*[@type=\"password\"]";
    private String enterPassButtonXpath = "//*[@data-testid=\"enter-password\"]";
    private String enterButtonXpath = "//*[@data-testid=\"login-to-mail\"]";
    //адреса для отправки нового письма
    private String createNewLetter = "//span[text()='Написать письмо']";


    public MainPage() {
        super("Главная", "https://mail.ru");
    }

    @Step("Логин в почту")
    public void login(String login, String password){
        sendKeys(By.xpath(loginFieldXpath), login);
        clickOnElement(By.xpath(enterPassButtonXpath));
        sendKeys(By.xpath(passwordFieldXpath),password);
        clickOnElement(By.xpath(enterButtonXpath));
    }

    @Step("Переходим к отправке нового письма")
    public void goToNewLetter(){
        clickOnElement(By.xpath(createNewLetter));
    }
}
