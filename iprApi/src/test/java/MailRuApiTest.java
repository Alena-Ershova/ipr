import apiUtils.MailRuApiUtils;
import apiUtils.MyCookies;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Letter;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static utils.TestDataStorage.getLogin;
import static utils.TestUtils.createString;

@Epic("Тестирование почты mail.ru")
@Feature("Mail.ru")
public class MailRuApiTest{
    private static String token;
    private static MailRuApiUtils apiUtil = new MailRuApiUtils();

    @Step("Логинимся в почту")
    @BeforeAll
    public static void mailRuLogin() {
        token = apiUtil.login();
    }

    @DisplayName("Получаем входящие письма")
    @Tag("api")
    @Test
    public void getLettersTest() {
        Response responseBody = apiUtil.getLetters(token);
        Assertions.assertEquals(200, responseBody.jsonPath().getInt("status"));
        List <String> threads = responseBody.jsonPath().getList("body.threads");
        assertFalse(threads.isEmpty());
    }

    @DisplayName("Отправляем письмо")
    @Test
    public void sendLettersTest() {
        Letter letter = new Letter(getLogin("default"), createString(), createString(), null);
        Response responseBody = apiUtil.sendLetter(letter, token);
        Assertions.assertEquals(200, responseBody.jsonPath().getInt("status"));
    }

    @AfterAll
    public static synchronized void clean() {
        MyCookies.clearCookies();
    }
}
