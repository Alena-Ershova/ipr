package apiUtils;

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
    }

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

    public ValidatableResponse checkLetter(){
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
}

