package apiUtils;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static utils.TestUtils.createString;

public class PostShiftApiUtils {
    private final static String HASH = "e0c9d02ddb72226c384758097db0045d";

    public static String createMail(){
        RestAssured.baseURI = "https://post-shift.ru";
        RestAssured.useRelaxedHTTPSValidation();
        ValidatableResponse response = given()
                .queryParam("action", "new")
                .queryParam("hash", HASH)
                .queryParam("name", createString())
                .when().log().all().get("/api.php")
                .then().log().all().statusCode(200);
        return response.extract().response().jsonPath().get("key");
    }
}
