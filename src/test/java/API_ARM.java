import io.restassured.http.Headers;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static java.util.Objects.isNull;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

public class API_ARM {
    private static final String BASE_URI = "http://213.128.208.33:8080";

    /**
     * Получение ID сессии (аутентификация)
     */
    @Test
    public void test_GetSessionID(){
        Map<String, Object> paramsReqMap = new HashMap<>();
        paramsReqMap.put("success", "/share/page/");
        paramsReqMap.put("failure", "/share/page/?error=true");
        paramsReqMap.put("username", "proninin");
        paramsReqMap.put("password", "12345");

        Response response = null;
        try {
            response =
                    given()
                            .params(paramsReqMap)
                    .post(BASE_URI)
                    .then().statusCode(200).extract().response();
        } catch(java.lang.AssertionError e){
            System.out.println("Autorisation: "+ e.getMessage());
        }

        if (isNull(response)) {
            System.out.println("Response is null, sessionId not found ");
            return;
        }
        String sessionId = response.getSessionId();
        System.out.println(sessionId);
     }
}
