package wrapper;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Класс-обертка для web driver
 * реализует Singleton и базовые методы работы с драйвером
 */
public class DriverWrapper {
    private RemoteWebDriver driver = null;
    private WebDriverWait wait = null;
    private static DriverWrapper driverWrapper = null;
    boolean color = false;

    private DriverWrapper() {
        this.driver = new SafariDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        color = true;
        this.wait = new WebDriverWait(driver, 10, 200);
    }

    public static DriverWrapper get() {
        if (driverWrapper == null) {
            driverWrapper = new DriverWrapper();
        }
        return driverWrapper;
    }

    public DriverWrapper openURL(String url) {
        driver.navigate().to(url);
        return this;
    }

    public DriverWrapper close() {
        driver.close();
        return this;
    }

    public void quit() {
        driver.quit();
        driverWrapper = null;
    }

    public WebElement getElement(By locator) throws TimeoutException{
        WebElement element = null;
        int maxCount = 3;
        for (int i = 0; i < maxCount; i++) {
            try {
                element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                if (color) {
                    driver.executeScript("arguments[0]['style']['backgroundColor']='blue';", element);
                }
                return element;
            } catch (StaleElementReferenceException e) {
            } catch (WebDriverException e) {
                System.out.println("esdfs");
            }
        }
        throw new NoSuchElementException("Элемент не найден");
    }

    public List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    public void scroll(int pix) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(String.format("window.scrollBy(0,%d)", pix));
    }
}
