package uiTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import pages.BasicPage;
import pages.MainPage;
import utils.LoginData;

import java.io.File;
import java.io.IOException;

import static utils.cipher.EncryptionUtils.decrypt;
import static utils.cipher.EncryptionUtils.getKeyFromFile;

public class BaseTest {
    protected static BasicPage page;

    @BeforeAll
    public static void baseSetUp(){
        page = new MainPage();
    }
    @AfterAll
    public static void close(){
        page.close();
    }

    public static String getEmail(){
        ObjectMapper objectMapper = new ObjectMapper();
        String login = "";
        try {
            LoginData loginData = objectMapper.readValue(new File("src/test/resources/LoginData.json"), LoginData.class);
            login = decrypt(loginData.getLogin(), getKeyFromFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return login;
    }
}
