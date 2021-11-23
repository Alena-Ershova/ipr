package apiTests;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import models.Letter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pages.MainPage;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LettersApiTest extends BaseApiTest {
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
                .queryParam("name", createName())
                .when().log().all().get("/api.php")
                .then().log().all().statusCode(200);
        key = response.extract().response().jsonPath().get("key");
        address = response.extract().response().jsonPath().get("email");
    }

    @Step("Получаем входящие письма")
    @Test
    public void getLettersTest() {
        given()
                .queryParam("action", "getlist")
                .queryParam("hash", HASH)
                .queryParam("key", key)
                .when().log().all().get("/api.php")
                .then().log().all().statusCode(200);
    }

    @Step("Получаем входящие письма после отправки нового письма")
    @Test
    public void receiveNewLetterTest() {
        MainPage page = new MainPage();
        Letter letter = new Letter(address, createName(), createName(), null);
        page.sendLetterWithoutCopies(letter);
        //письмо отправляется не сразу, поэтому приходится ждать
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ValidatableResponse listResponse = given()
                .queryParam("action", "getlist")
                .queryParam("hash", HASH)
                .queryParam("key", key)
                .when().log().all().get("/api.php")
                .then().log().all().statusCode(200);
        int id = listResponse.extract().response().jsonPath().getInt("[0].id");
        ValidatableResponse response = given()
                .queryParam("action", "getmail")
                .queryParam("hash", HASH)
                .queryParam("key", key)
                .queryParam("id", id)
                .when().log().all().get("/api.php")
                .then().log().all().statusCode(200);
        assertTrue(response.extract().response().jsonPath().get("message").toString().contains(letter.getText()));
    }

    @AfterAll
    public static void close() {
        MainPage page = new MainPage();
        page.close();
    }
}
