import apiUtils.MailRuApiUtils;
import apiUtils.MyCookies;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import models.Letter;
import models.MessageHeader;
import models.MessageTextResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static apiUtils.PostShiftApiUtils.createMail;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.TestDataStorage.getLogin;
import static utils.TestUtils.createString;

@Epic("Тестирование почты post-shift")
@Feature("Получение списка писем")
public class LettersApiTest {
    private final static String HASH = "e0c9d02ddb72226c384758097db0045d";
    private static String key;
    private static MailRuApiUtils apiUtil = new MailRuApiUtils();

    @Step("Создаем почту")
    @BeforeAll
    public static void setMail() {
        key = createMail();
    }

    @DisplayName("Получаем входящие письма")
    @Test
    public void getLettersTest() {
        given()
                .queryParam("action", "getlist")
                .queryParam("hash", HASH)
                .queryParam("key", key)
                .when().log().all().get("/api.php")
                .then().log().all().statusCode(200);
    }

    @DisplayName("Получаем входящие письма после отправки нового письма")
    @Test
    public void receiveNewLetterTest() {
        Letter letter = new Letter(getLogin("default"), createString(), createString(), null);
        String token = apiUtil.login();
        apiUtil.sendLetter(letter, token);
        ValidatableResponse listResponse = given()
                .queryParam("action", "getlist")
                .queryParam("hash", HASH)
                .queryParam("key", key)
                .when().log().all().get("/api.php")
                .then().log().all().statusCode(200);
        MessageHeader[] messageHeaders = listResponse.extract().body().as(MessageHeader[].class);
        ValidatableResponse response = given()
                .queryParam("action", "getmail")
                .queryParam("hash", HASH)
                .queryParam("key", key)
                .queryParam("id", messageHeaders[0].getId())
                .when().log().all().get("/api.php")
                .then().log().all().statusCode(200);
        assertTrue(response.extract().body().as(MessageTextResponse.class).getMessage().contains(letter.getText()));
    }

    @AfterAll
    public static void close() {
        MyCookies.clearCookies();
    }
}
