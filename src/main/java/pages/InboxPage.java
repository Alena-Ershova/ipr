package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class InboxPage extends BasicPage{
    private String inboxTextXpath = "//*[text()=\"Входящие\"]";
    private String letterXpath = "//span[contains(@class, 'llc__subject')]";

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
}
