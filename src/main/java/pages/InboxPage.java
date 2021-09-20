package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class InboxPage extends BasicPage{
    private String inboxTextXpath = "//*[text()=\"Входящие\"]";
    private String letterXpath = "//span[contains(@class, 'llc__subject')]";

    public InboxPage() {
        super("Входящие", "https://e.mail.ru/inbox");
    }

    public void loginSuccessful(){
        assertVisible(By.xpath(inboxTextXpath));
    }

    public void printLetters(){
        List<WebElement> letters = driver.findElements(By.xpath(letterXpath));
        for(WebElement element : letters){
            System.out.println(getText(element));
        }
    }
}
