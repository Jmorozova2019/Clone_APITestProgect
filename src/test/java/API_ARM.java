import io.restassured.http.Cookie;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import org.testng.annotations.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class API_ARM {
    private static final String BASE_URL = "http://213.128.208.33:8080";

    @Test
    public void testAutorisation(){
        Map<String, Object> paramsReqMap = new HashMap<>();
        paramsReqMap.put("success", "/share/page/");
        paramsReqMap.put("failure", "/share/page/?error=true");
        paramsReqMap.put("username", "proninin");
        paramsReqMap.put("password", "12345");

        Response response = null;
        try{
            response =
                    given()
                    .params(paramsReqMap)
                    .post(BASE_URL)
                    .then().statusCode(200).extract().response();
        }catch(java.lang.AssertionError e){
            System.out.println("Autorisation: "+ e.getMessage());
        }
        System.out.println("StatusCode: "+ response.getStatusCode());

        Headers headerResponse = response.getHeaders();
        //почему-то возвращает только один набор Set-Cookie
        //headerResponse.forEach(h ->  System.out.println(h.getName()));
        //List<String> listSetCookies= headerResponse.getValues("Set-Cookie");
        //System.out.println(listSetCookies.size());
        //listSetCookies.forEach(u -> System.out.println(u));

        Map<String, String> allCookies = response.getCookies();
        System.out.println("----------Cookies--------------");
        allCookies.forEach((a, b) ->  System.out.println(a + ": " + b));

        return;
    }
}
