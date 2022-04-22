import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import models.Letter;
import org.junit.jupiter.api.*;

import java.util.*;

import static apiUtils.ApiLogin.login;
import static apiUtils.ApiLogin.sendLetter;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static utils.TestDataStorage.getLogin;
import static utils.TestUtils.createString;

@Epic("Тестирование почты mail.ru")
@Feature("Mail.ru")
public class MailRuApiTest{
    private static Cookies cookies;
    private static List<Cookie> cookieList = new LinkedList<>();
    private static String token;

    @Step("Логинимся в почту")
    @BeforeAll
    public static void mailRuLogin() {
        token = login(cookieList);
    }

    @DisplayName("Получаем входящие письма")
    @Tag("api")
    @Test
    public void getLettersTest() {
        cookies = new Cookies(cookieList);
        Response responseBody =
                given()
                .cookies(cookies)
                .queryParam("folder", "0")
                .queryParam("limit", "50")
                .queryParam("last_modified", "1645100561")
                .queryParam("force_custom_thread", "true")
                .queryParam("offset", "0")
                .queryParam("email", getLogin("default"))
                .queryParam("htmlencoded", "false")
                .queryParam("token", token)
                .queryParam("_", "1645100701077")
                .when().log().all().get("/api/v1/k8s/threads/status/smart")
                .then().log().all().statusCode(200).extract().response();
        Assertions.assertEquals(200, responseBody.jsonPath().getInt("status"));
        List <String> threads = responseBody.jsonPath().getList("body.threads");
        assertFalse(threads.isEmpty());
    }

    @DisplayName("Отправляем письмо")
    @Test
    public void sendLettersTest() {
        Letter letter = new Letter(getLogin("default"), createString(), createString(), null);
        Response responseBody = sendLetter(letter, token, cookieList);
        Assertions.assertEquals(200, responseBody.jsonPath().getInt("status"));
    }

    @AfterAll
    public static synchronized void clean() {
        cookies = null;
        cookieList.clear();
    }
}
