package testMethods.common;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static java.util.Objects.isNull;

/**
 * Класс для переиспользуемых методов
 */
public class Common {
    /**
     * Получение ID сессии
     */
    public static String getSessionID(String BASE_URI) {
        Response response = null;
        try {
            response =
                given().baseUri(BASE_URI)
                .when()
                        .get()
                .then().extract().response();
        } catch (java.lang.AssertionError e) {
            System.out.println("Getting session: " + e.getMessage());
        }

        if (isNull(response)){
            return null;
        }

        return response.getSessionId();
    }
}
