package apiTests;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class LettersApiTest extends BaseApiTest{
    private final static String HASH = "851d352505ada46a8c7d92dc9a83be1a";
    private static String key;

    /**
     * Создание почты для дальнейшего
     * использования
     */
    @Before
    public void createMail() {
        RestAssured.baseURI = "https://post-shift.ru";
        RestAssured.useRelaxedHTTPSValidation();
        ValidatableResponse response = given()
                .queryParam("action", "new")
                .queryParam("hash", HASH)
                .queryParam("name", createName())
                .when().log().all().get("/api.php")
                .then().log().all().statusCode(200);
        key = response.extract().response().jsonPath().get("key");
    }

    /**
     * Проверка получения списка писем
     */
    @Test
    public void getLettersTest() {
        given()
                .queryParam("action", "getlist")
                .queryParam("hash", HASH)
                .queryParam("key", key)
                .when().log().all().get("/api.php")
                .then().log().all().statusCode(200);
    }
}
