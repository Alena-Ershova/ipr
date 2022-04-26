package apiUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import models.Letter;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static io.restassured.RestAssured.*;
import static utils.TestDataStorage.getLogin;
import static utils.TestDataStorage.getPassword;

public class MailRuApiUtils {
    private String token;
    private MyCookies myCookies = new MyCookies();

    public void login(){
        authorization();
        ValidatableResponse response = given()
                .baseUri("https://mail.ru")
                .cookies(myCookies.getCookies())
                .when().log().all().get("/")
                .then().log().all().statusCode(200);
        String page = response.extract().body().asString();
        assertTrue(page.contains(getLogin("default")));
        getToken();
    }

    private void authorization(){
        RestAssured.useRelaxedHTTPSValidation();
        ValidatableResponse response = given()
                .baseUri("https://mail.ru")
                .when().log().all().get("/")
                .then().log().all().statusCode(200);
        myCookies.setCookies(response.extract().response().getDetailedCookies());
        String token = myCookies.getCookies().getValue("act");
        response = given()
                .baseUri("https://auth.mail.ru")
                .queryParam("login", getLogin("default"))
                .cookies(myCookies.getCookies())
                .when().log().all().get("/api/v1/pushauth/info")
                .then().log().all().statusCode(200);
        myCookies.setCookies(response.extract().response().getDetailedCookies());
        response = given()
                .baseUri("https://auth.mail.ru")
                .cookies(myCookies.getCookies())
                .contentType("application/x-www-form-urlencoded")
                .header("Referer", "https://mail.ru/")
                .formParam("login", getLogin("default"))
                .formParam("password", getPassword("default"))
                .formParam("saveauth", "1")
                .formParam("token", token)
                .formParam("project", "e.mail.ru")
                .when().log().all().post("/jsapi/auth")
                .then().log().all().statusCode(200);
        myCookies.setCookies(response.extract().response().getDetailedCookies());
    }

    private String getToken(){
        String updateToken;
        ValidatableResponse response = given()
                .baseUri("https://auth.mail.ru")
                .cookies(myCookies.getCookies())
                .redirects().follow(false)
                .queryParam("from", "https://e.mail.ru/messages/inbox")
                .header("Host", "auth.mail.ru")
                .when().log().all().get("/sdc")
                .then().log().all().statusCode(302);
        String location = response.extract().header("Location");
        myCookies.setCookies(response.extract().response().getDetailedCookies());
        response = given()
                .cookies(myCookies.getCookies())
                .redirects().follow(false)
                .header("Host", "e.mail.ru")
                .header("Referer","https://e.mail.ru/sdc?from=https://e.mail.ru/messages/inbox")
                .when().log().all().get(location)
                .then().log().all().statusCode(302);
        myCookies.setCookies(response.extract().response().getDetailedCookies());
        RestAssured.useRelaxedHTTPSValidation();
        response = given()
                .baseUri("https://e.mail.ru")
                .cookies(myCookies.getCookies())
                .header("host", "e.mail.ru")
                .when().log().all().get("/inbox")
                .then().log().all().statusCode(200);
        updateToken = response.extract().body().asString();
        String beginning = "patron.updateToken(\"";
        int index = updateToken.indexOf(beginning)+beginning.length();
        token = updateToken.substring(index, updateToken.indexOf("\"", index+beginning.length()));
        return token;
    }

    public Response sendLetter(Letter letter, String address){
        RestAssured.useRelaxedHTTPSValidation();
        Response responseBody = given().cookies(myCookies.getCookies())
                .baseUri("https://e.mail.ru")
                .header("origin", "https://e.mail.ru")
                .header("referer", "https://e.mail.ru/inbox/")
                .contentType("application/x-www-form-urlencoded")
                .formParam("body", "{\"html\":\"<div>"+letter.getText()+"</div>\",\"text\":\""+letter.getText()+"\"}")
                .formParam("id", "8bA78cBd6424961EBcA2296982D412c0")
                .formParam("from", letter.getAddress())
                .formParam("subject", letter.getSubject())
                .formParam("correspondents", "{\"to\":\"<"+address+">\",\"cc\":\"\",\"bcc\":\"\"}")
                .formParam("sending", "true")
                .formParam("email", address)
                .formParam("attaches", "{\"list\":[]}")
                .formParam("token", token)
                .when().log().all().post("api/v1/messages/send")
                .then().log().all().statusCode(200).extract().response();
        Assertions.assertEquals(200, responseBody.jsonPath().getInt("status"));
        return responseBody;
    }

    public Response getLetters(){
        return given()
                        .baseUri("https://e.mail.ru")
                        .cookies(myCookies.getCookies())
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
    }

    public MyCookies getCookies(){
        return myCookies;
    }
}
