import apiUtils.MailRuApiUtils;
import apiUtils.PostShiftApiUtils;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import models.Letter;
import models.MessageTextResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.TestDataStorage.getLogin;
import static utils.TestUtils.createString;

@Epic("Тестирование почты post-shift")
@Feature("Получение списка писем")
public class LettersApiTest {
    private static MailRuApiUtils apiUtil = new MailRuApiUtils();
    private static PostShiftApiUtils postShift = new PostShiftApiUtils();

    @Step("Создаем почту")
    @BeforeAll
    public static void setMail() {
        postShift.createMail();
    }

    @DisplayName("Получаем входящие письма")
    @Test
    public void getLettersTest() {
        postShift.getLetters();
    }

    @DisplayName("Получаем входящие письма после отправки нового письма")
    @Test
    public void receiveNewLetterTest() {
        Letter letter = new Letter(getLogin("default"), createString(), createString(), null);
        apiUtil.login();
        apiUtil.sendLetter(letter);
        ValidatableResponse response = postShift.checkLetter();
        assertTrue(response.extract().body().as(MessageTextResponse.class).getMessage().contains(letter.getText()));
    }

    @AfterAll
    public static void close() {
        apiUtil.getCookies().clearCookies();
    }
}
