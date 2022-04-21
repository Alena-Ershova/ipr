import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static utils.TestDataStorage.getLogin;
import static utils.TestDataStorage.getPassword;

@Epic("Тестирование почты mail.ru")
@Feature("")
public class MailRuApiTest{
    private static Cookies cookies;
    private static List<Cookie> cookieList;

    @Step("Логинимся в почту")
    @BeforeAll
    public static void mailRuLogin() {
        cookieList = new LinkedList<>();
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
        Assertions.assertTrue(page.contains(getLogin("default")));
        cookieList = cookieList.stream().filter(o->!o.getDomain().contains("auth")).collect(Collectors.toList());
        cookies = new Cookies(cookieList);
        RestAssured.baseURI = "https://e.mail.ru";
        response = given()
                .cookies(cookies)
                .redirects().follow(false)
                .queryParam("from", "https://e.mail.ru/messages/inbox")
                .header("Host", "auth.mail.ru")
                .when().log().all().get("/sdc")
                .then().log().all().statusCode(302);
        String location = response.extract().header("Location");
        cookieList.addAll(response.extract().response().getDetailedCookies().asList());
        cookies = new Cookies(cookieList);
        response = given()
                .cookies(cookies)
                .redirects().follow(false)
                .header("Host", "e.mail.ru")
                .header("Referer","https://e.mail.ru/sdc?from=https://e.mail.ru/messages/inbox")
                //.queryParam("from", "https%3A%2F%2Fe.mail.ru%2Fmessages%2Finbox%3Fapp_id_mytracker%3D58519%26authid%3Dl0gpty15.fr%26back%3D1%26dwhsplit%3Ds10273.b1ss12743s%26from%3Dlogin%26x-login-auth%3D1%26back%3D1%26from%3Dnavi")
                .when().log().all().get(location)
                .then().log().all().statusCode(302);
        cookieList.addAll(response.extract().response().getDetailedCookies().asList());
        cookies = new Cookies(cookieList);
    }

    @DisplayName("Получаем входящие письма")
    @Tag("api")
    @Test
    public void getLettersTest() {
        RestAssured.baseURI = "https://e.mail.ru";
        RestAssured.useRelaxedHTTPSValidation();
        //cookieList = cookieList.stream().filter(o->!o.getDomain().contains("auth")).collect(Collectors.toList());
        //cookies = new Cookies(cookieList);
        ValidatableResponse response = given()
                .cookies(cookies)
                //.cookie("s","new_light=1")
                //.cookie("sdcs","B4tfSQC1l5dmTVZh")
                .header("host", "e.mail.ru")
                .when().log().all().get("/inbox")
                .then().log().all().statusCode(200);
        String token = response.extract().body().asString();

        File file = new File("test.html");
        try {
            Writer writer = new FileWriter(file);
            writer.write(token);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int index = token.indexOf("patron.updateToken(\"");
        token = token.substring(index, token.indexOf("\"", index));

        System.out.println(token);

        given()
                .cookies(cookies)
                .queryParam("folder", "0")
                .queryParam("limit", "50")
                .queryParam("filters", "%7B%7D")
                .queryParam("last_modified", "1645100561")
                .queryParam("force_custom_thread", "true")
                .queryParam("supported_custom_metathreads", "[%22tomyself%22]")
                .queryParam("offset", "0")
                .queryParam("email", getLogin("default"))
                .queryParam("htmlencoded", "false")
                .queryParam("token", token)
                .queryParam("_", "1645100701077")
                .when().log().all().get("/api/v1/k8s/threads/status/smart")
                .then().log().all().statusCode(200);
    }

    @AfterAll
    public static synchronized void clean() {
        cookies = null;
        cookieList.clear();
    }
}
