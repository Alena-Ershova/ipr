package apiUtils;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import models.MessageHeader;
import utils.TestDataStorage;

import static io.restassured.RestAssured.given;
import static utils.TestUtils.createString;

/**
 * Класс, содержащий вспомогательные методы для
 * проерки работы api post-shift
 */
public class PostShiftApiUtils {
    private String key;
    private String email;

    @Step("Создаем почту")
    public void createMail() {
        RestAssured.useRelaxedHTTPSValidation();
        ValidatableResponse response = given()
                .baseUri("https://post-shift.ru")
                .queryParam("action", "new")
                .queryParam("hash", TestDataStorage.getHash("default"))
                .queryParam("name", createString())
                .when().log().all().get("/api.php")
                .then().log().all().statusCode(200);
        key = response.extract().response().jsonPath().get("key");
        email = response.extract().response().jsonPath().get("email");
    }

    @Step("Получаем список писем")
    public ValidatableResponse getLetters() {
        ValidatableResponse response = given()
                .baseUri("https://post-shift.ru")
                .queryParam("action", "getlist")
                .queryParam("hash", TestDataStorage.getHash("default"))
                .queryParam("key", key)
                .when().log().all().get("/api.php")
                .then().log().all().statusCode(200);
        return response;
    }

    @Step("Проверяем наличие письма")
    public ValidatableResponse checkLetter(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ValidatableResponse listResponse = getLetters();
        MessageHeader[] messageHeaders = listResponse.extract().body().as(MessageHeader[].class);
        ValidatableResponse response = given()
                .baseUri("https://post-shift.ru")
                .queryParam("action", "getmail")
                .queryParam("hash", TestDataStorage.getHash("default"))
                .queryParam("key", key)
                .queryParam("id", messageHeaders[0].getId())
                .when().log().all().get("/api.php")
                .then().log().all().statusCode(200);
        return response;
    }

    public String getEmail(){
        return email;
    }
}

