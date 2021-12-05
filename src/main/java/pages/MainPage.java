package pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import utils.LoginData;

import java.io.File;
import java.io.IOException;

import static utils.cipher.EncryptionUtils.decrypt;
import static utils.cipher.EncryptionUtils.getKeyFromFile;

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
    public void login(){
        ObjectMapper objectMapper = new ObjectMapper();
        String login = "";
        String password = "";
        try {
            LoginData loginData = objectMapper.readValue(new File("src/test/resources/LoginData.json"), LoginData.class);
            login = decrypt(loginData.getLogin(), getKeyFromFile());
            password = decrypt(loginData.getPassword(), getKeyFromFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
