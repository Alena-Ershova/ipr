import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import models.Letter;
import models.MessageHeader;
import models.MessageTextResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.NewLetterPage;

import java.util.LinkedList;
import java.util.List;

import static apiUtils.ApiLogin.login;
import static apiUtils.ApiLogin.sendLetter;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.TestDataStorage.getLogin;
import static utils.TestUtils.createString;

@Epic("Тестирование почты post-shift")
@Feature("Получение списка писем")
public class LettersApiTest {
    private final static String HASH = "e0c9d02ddb72226c384758097db0045d";
    private static String key;
    private static String address;

    @Step("Создаем почту")
    @BeforeAll
    public static void createMail() {
        RestAssured.baseURI = "https://post-shift.ru";
        RestAssured.useRelaxedHTTPSValidation();
        ValidatableResponse response = given()
                .queryParam("action", "new")
                .queryParam("hash", HASH)
                .queryParam("name", createString())
                .when().log().all().get("/api.php")
                .then().log().all().statusCode(200);
        key = response.extract().response().jsonPath().get("key");
        address = response.extract().response().jsonPath().get("email");
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
        List<Cookie> cookieList = new LinkedList<>();
        String token = login(cookieList);
        sendLetter(letter, token, cookieList);
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
        MainPage page = new MainPage();
        page.close();
    }
}
