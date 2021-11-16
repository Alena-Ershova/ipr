package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class MainPage extends BasicPage{
    private String loginFieldXpath = "//input[@name=\"login\"]";
    private String domenFieldXpath = "//form[@data-testid=\"logged-out-form\"]//div[2]";
    private String passwordFieldXpath = "//*[@type=\"password\"]";
    private String enterPassButtonXpath = "//*[@data-testid=\"enter-password\"]";
    private String enterButtonXpath = "//*[@data-testid=\"login-to-mail\"]";
    //адреса для отправки нового письма
    private String createNewLetter = "//span[text()='Написать письмо']";
    private String receiverNewLetter = "//input[contains(@class,'container')]";
    private String topicNewLetter = "//input[@name='Subject']";
    private String textNewLetter = "//div[@role='textbox']";
    private String sendNewLetter = "//span[text()='Отправить']";
    // данные
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

    @Step("Отправляем письмо на адрес {address}")
    public void sendLetter(String address){
        open();
        login();
        clickOnElement(By.xpath(createNewLetter));
        sendKeys(By.xpath(receiverNewLetter),address);
        sendKeys(By.xpath(topicNewLetter),address);
        sendKeys(By.xpath(topicNewLetter),address);
        sendKeys(By.xpath(textNewLetter),address);
        clickOnElement(By.xpath(sendNewLetter));
    }
}
