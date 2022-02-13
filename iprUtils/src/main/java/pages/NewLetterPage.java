package pages;

import io.qameta.allure.Step;
import models.DBLetter;
import models.Letter;
import org.openqa.selenium.By;

public class NewLetterPage extends BasicPage {
    public NewLetterPage() {
        super("Новое письмо", "https://e.mail.ru/inbox/");
    }

    private String receiverNewLetter = "//input[contains(@class,'container')]";
    private String topicNewLetter = "//input[@name='Subject']";
    private String textNewLetter = "//div[@role='textbox']";
    private String sendNewLetter = "//span[text()='Отправить']/..";
    private String closeNewLetterSent = "//*[@class='ico ico_16-close ico_size_s']";

    @Step("Отправляем письмо без копий")
    public void sendLetterWithoutCopies(Letter letter) {
        sendKeys(By.xpath(receiverNewLetter), letter.getAddress());
        sendKeys(By.xpath(topicNewLetter), letter.getSubject());
        sendKeys(By.xpath(textNewLetter), letter.getText());
        clickOnElement(By.xpath(sendNewLetter));
        clickOnElement(By.xpath(closeNewLetterSent));
    }

    @Step("Отправляем письмо без копий из базы данных")
    public void sendLetterWithoutCopies(DBLetter letter) {
        sendKeys(By.xpath(receiverNewLetter), letter.getEmail());
        sendKeys(By.xpath(topicNewLetter), letter.getSubject());
        sendKeys(By.xpath(textNewLetter), letter.getContent());
        clickOnElement(By.xpath(sendNewLetter));
        clickOnElement(By.xpath(closeNewLetterSent));
    }
}
