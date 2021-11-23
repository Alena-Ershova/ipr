package pages;

import io.qameta.allure.Step;
import models.Letter;
import org.openqa.selenium.By;
import utils.Data;

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
    private String closeNewLetterSent = "//*[@class='ico ico_16-close ico_size_s']";

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

    @Step("Отправляем письмо на адрес {address}")
    public void sendLetterWithoutCopies(Letter letter){
        open();
        login(Data.login, Data.password);
        clickOnElement(By.xpath(createNewLetter));
        sendKeys(By.xpath(receiverNewLetter), letter.getAddress());
        sendKeys(By.xpath(topicNewLetter), letter.getSubject());
        sendKeys(By.xpath(textNewLetter), letter.getText());
        clickOnElement(By.xpath(sendNewLetter));
        clickOnElement(By.xpath(closeNewLetterSent));
    }
}
