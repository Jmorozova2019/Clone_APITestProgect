package testMethods.authorization;

import static java.util.Objects.isNull;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static testMethods.common.Common.getSessionID;

import java.util.Map;

/**
 * Класс с методами авторизации и полями, которые к ней относятся
 */

public class Authorization {
    public static String LOCAL_URL = "/share/page/dologin";

    public static Response authorization(String BASE_URI, Map<String, String> params) {
        String sessionID = getSessionID(BASE_URI);
        if(isNull(sessionID))
        {
            return null;
        }

        Response response = null;
        try {
            response =
                given().baseUri(BASE_URI)
                    .sessionId(sessionID)
                    .params(params)
                .when()
                    .post(LOCAL_URL)
                .then().extract().response();
        } catch (java.lang.AssertionError e) {
            System.out.println("Authorization: " + e.getMessage());
            return null;
        }

        return response;
    }

    public static Response authorization(String BASE_URI, boolean isValid) {
        Map<String, String> params = null;
        if (isValid){
            params = parameters.request.authorization.Authorization.getValidParameters();
        }
        else {
            params = parameters.request.authorization.Authorization.getInvalidParameters();
        }
        return authorization(BASE_URI, params);
    }


    public static Response exit(String BASE_URI) {
        Map<String, String> params = parameters.request.authorization.Authorization.getValidParameters();

        Response response = authorization(BASE_URI, params);
        //if(!checkHttpResponce(response, "Ошибка авторизации"){
        //    return null;
        //}

        //запрос на выход - не реализовано
        return response;
    }

}