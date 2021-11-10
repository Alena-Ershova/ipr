package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import wrapper.DriverWrapper;

public class BasicPage {
    //общие методы
    protected DriverWrapper driver;
    protected String pageName;
    protected String address;

    protected BasicPage(String pageName, String address) {
        this.driver = DriverWrapper.get();
        this.pageName = pageName;
        this.address = address;
    }

    public void close(){
        driver.quit();
    }

    public void open(){
        driver.openURL(this.address);
    }

    public void clickOnElement(By locator){
        driver.getElement(locator).click();
    }

    public void sendKeys(By locator, String keys){
        driver.getElement(locator).sendKeys(keys);
    }

    public void assertVisible(By locator){
        Assertions.assertTrue(driver.getElement(locator).isDisplayed());
    }

    public String getText(WebElement element){
        return element.getText();
    }

    public String getText(By locator){
        return driver.getElement(locator).getText();
    }
}
