package apiUtils;

import io.restassured.http.Cookie;
import io.restassured.http.Cookies;

import java.util.LinkedList;
import java.util.List;

public class MyCookies {
    private List<Cookie> cookieList = new LinkedList<>();

    public void setCookies(Cookies cookies){
        cookieList.addAll(cookies.asList());
    }

    public Cookies getCookies(){
        return new Cookies(cookieList);
    }

    public void clearCookies(){
        cookieList.clear();
    }
}
