package pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class InboxPage extends BasicPage{
    private String inboxTextXpath = "//*[text()=\"Входящие\"]";
    private String letterXpath = "//span[contains(@class, 'llc__subject')]";
    private String letterText = "//div[contains(@id,'BODY')]";
    private String deleteLetter = "//span[@title='Удалить']";

    public InboxPage() {
        super("Входящие", "https://e.mail.ru/inbox");
    }

    @Step("Проверка успешности логина")
    public void loginSuccessful(){
        assertVisible(By.xpath(inboxTextXpath));
    }

    @Step("Выводим список входящих писем")
    public void printLetters(){
        List<WebElement> letters = driver.findElements(By.xpath(letterXpath));
        for(WebElement element : letters){
            System.out.println(getText(element));
        }
    }

    @Step("Открываем письмо с темой {subject}")
    public void clickOnLetterBySubject(String subject){
        clickOnElement(By.xpath("//div[@aria-label=\"grid\"]//span[text()='"+subject+"']/../.."));
    }

    @Step("Проверям, что текст письма содержит {text}")
    public void checkLetterText(String text){
        Assertions.assertTrue(getText(By.xpath(letterText)).contains(text));
    }

    @Step("Удаляем открытое письмо")
    public void deleteOpenedLetter(){
        clickOnElement(By.xpath(deleteLetter));
    }
}
