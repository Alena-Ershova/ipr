package apiUtils;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import models.Letter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.TestDataStorage.getLogin;
import static utils.TestDataStorage.getPassword;
import static io.restassured.RestAssured.*;
import static utils.TestUtils.createString;

public class ApiLogin {

    public static String login(List<Cookie> cookieList){
        String updateToken;
        Cookies cookies = new Cookies();
        RestAssured.baseURI = "https://mail.ru";
        RestAssured.useRelaxedHTTPSValidation();
        ValidatableResponse response = given()
                .when().log().all().get("/")
                .then().log().all().statusCode(200);
        cookieList.addAll(response.extract().response().getDetailedCookies().asList());
        cookies = new Cookies(cookieList);
        String token = cookies.getValue("act");
        RestAssured.baseURI = "https://auth.mail.ru";
        response = given()
                .queryParam("login", getLogin("default"))
                .cookies(cookies)
                .when().log().all().get("/api/v1/pushauth/info")
                .then().log().all().statusCode(200);
        cookieList.addAll(response.extract().response().getDetailedCookies().asList());
        cookies = new Cookies(cookieList);
        response = given()
                .cookies(cookies)
                .contentType("application/x-www-form-urlencoded")
                .header("Referer", "https://mail.ru/")
                .formParam("login", getLogin("default"))
                .formParam("password", getPassword("default"))
                .formParam("saveauth", "1")
                .formParam("token", token)
                .formParam("project", "e.mail.ru")
                .when().log().all().post("/jsapi/auth")
                .then().log().all().statusCode(200);
        cookieList.addAll(response.extract().response().getDetailedCookies().asList());
        cookies = new Cookies(cookieList);
        RestAssured.baseURI = "https://mail.ru";
        response = given()
                .cookies(cookies)
                .when().log().all().get("/")
                .then().log().all().statusCode(200);
        String page = response.extract().body().asString();
        assertTrue(page.contains(getLogin("default")));
//        cookieList = cookieList.stream().filter(o->!o.getDomain().contains("auth")).collect(Collectors.toList());
//        cookies = new Cookies(cookieList);
        RestAssured.baseURI = "https://auth.mail.ru";
        response = given()
                .cookies(cookies)
                .redirects().follow(false)
                .queryParam("from", "https://e.mail.ru/messages/inbox")
                .header("Host", "auth.mail.ru")
                .when().log().all().get("/sdc")
                .then().log().all();//.statusCode(302);
        String location = response.extract().header("Location");
        cookieList.addAll(response.extract().response().getDetailedCookies().asList());
        cookies = new Cookies(cookieList);
        response = given()
                .cookies(cookies)
                .redirects().follow(false)
                .header("Host", "e.mail.ru")
                .header("Referer","https://e.mail.ru/sdc?from=https://e.mail.ru/messages/inbox")
                .when().log().all().get(location)
                .then().log().all().statusCode(302);
        cookieList.addAll(response.extract().response().getDetailedCookies().asList());
        cookies = new Cookies(cookieList);
        RestAssured.baseURI = "https://e.mail.ru";
        RestAssured.useRelaxedHTTPSValidation();
        response = given()
                .cookies(cookies)
                .header("host", "e.mail.ru")
                .when().log().all().get("/inbox")
                .then().log().all().statusCode(200);
        updateToken = response.extract().body().asString();
        String begging = "patron.updateToken(\"";
        int index = updateToken.indexOf(begging);
        updateToken = updateToken.substring(index+begging.length(), updateToken.indexOf("\"", index+begging.length()));
        return updateToken;
    }

    public static Response sendLetter(Letter letter, String token, List<Cookie> cookieList){
        Cookies cookies = new Cookies();
        cookies = new Cookies(cookieList);
        RestAssured.baseURI = "https://e.mail.ru";
        RestAssured.useRelaxedHTTPSValidation();
        Response responseBody = given().cookies(cookies)
                .header("origin", "https://e.mail.ru")
                .header("referer", "https://e.mail.ru/inbox/?app_id_mytracker=58519&authid=l2a3clcz.hnc&back=1%2C1&dwhsplit=s10273.b1ss12743s&from=login%2Cnavi&x-login-auth=1")
                .contentType("application/x-www-form-urlencoded")
                .formParam("body", "{\"html\":\"<div>text</div>\",\"text\":\"text\"}")
                .formParam("id", "8bA78cBd6424961EBcA2296982D412c0")
                .formParam("from", letter.getAddress())
                .formParam("subject", letter.getSubject())
                .formParam("correspondents", "{\"to\":\"<"+letter.getAddress()+">\",\"cc\":\"\",\"bcc\":\"\"}")
                .formParam("sending", "true")
                .formParam("email", letter.getAddress())
                .formParam("attaches", "{\"list\":[]}")
                .formParam("token", token)
                .when().log().all().post("api/v1/messages/send")
                .then().log().all().statusCode(200).extract().response();
        return responseBody;
    }
}
