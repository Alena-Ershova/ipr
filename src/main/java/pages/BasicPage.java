package pages;

import io.qameta.allure.Step;
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

    @Step("Закрытие страницы")
    public void close(){
        driver.quit();
    }

    @Step("Открытие страницы")
    public void open(){
        driver.openURL(this.address);
    }

    @Step("Клик на элемент по адресу")
    public void clickOnElement(By locator){
        driver.getElement(locator).click();
    }

    @Step("Вводим текст {keys} по адресу")
    public void sendKeys(By locator, String keys){
        driver.getElement(locator).sendKeys(keys);
    }
    @Step("Проверка видимости элемента по адресу")
    public void assertVisible(By locator){
        Assertions.assertTrue(driver.getElement(locator).isDisplayed());
    }

    @Step("Получаем текст элемента")
    public String getText(WebElement element){
        return element.getText();
    }

    @Step("Получаем текст элемента по адресу")
    public String getText(By locator){
        return driver.getElement(locator).getText();
    }
}
