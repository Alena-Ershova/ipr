package apiUtils;

import io.restassured.http.Cookie;
import io.restassured.http.Cookies;

import java.util.LinkedList;
import java.util.List;

public class MyCookies {
    private static List<Cookie> cookieList = new LinkedList<>();

    public static void setCookies(Cookies cookies){
        cookieList.addAll(cookies.asList());
    }

    public static Cookies getCookies(){
        return new Cookies(cookieList);
    }

    public static void clearCookies(){
        cookieList.clear();
    }
}
