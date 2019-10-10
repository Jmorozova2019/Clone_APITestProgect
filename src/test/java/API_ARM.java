import java.util.Map;

import static Utils.Utils.checkHttpResponce;
import static java.util.Objects.isNull;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;
import org.testng.Assert;
import ParametersForTests.ParametersForAutorisation;

/**
 * Класс тестовых методов авторизации и переиспользуемых методов
 */
public class API_ARM {
    public static final String BASE_URI = "http://213.128.208.33:8080";

    ///*****************************************************************************************************************
    // Начало Блока переиспользуемых функций
    //******************************************************************************************************************
    /**
     * Получение ID сессии
     */
    public static String getSessionID() {
        String sessionID = null;
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

        if (!checkHttpResponce(response, "Получить SESSIONID не удалось"))
            return null;

        return response.getSessionId();
    }

    /**
     * Авторизация  /share/page/
     */
    public static Response authorization(String sessionID, Map<String, String> paramsForAuthorisation) {
        Response response = null;
        try {
            response =
                given().baseUri(BASE_URI)
                    .sessionId(sessionID)
                    .params(paramsForAuthorisation)
                .when()
                    .post("/share/page/dologin")
                .then().extract().response();
        } catch (java.lang.AssertionError e) {
            System.out.println("Authorization: " + e.getMessage());
            return null;
        }

        if (!checkHttpResponce(response, "Авторизоваться не удалось"))
            return null;

        return response;
    }

    //*****************************************************************************************************************
    // Конец Блока переиспользуемых функций
    //******************************************************************************************************************

    /**
     * Проверка, что возвращается sessionID (никаких данных для этого не нужно - только путь)
     */
    @Test(enabled = false)
    public void test_checkGetSessionID() {
        String sessionID = getSessionID();
        Assert.assertFalse(sessionID.isEmpty(), "SessionID not found");
    }

    /**
     * Проверка формы авторизации (в сценарии также требуется проверка эламентов - не реализовано)
     * Можно отключать - он будет совпадать с тестом test_checkAuthorizationWithValidParam
     * или test_checkAuthorizationWithInvalidParam
     */
    @Test(enabled = true)
    public void test_checkAuthorizationWindowItems() {
        String sessionID = getSessionID();

        Response responsePage = null;
        try {
            responsePage =
                given().baseUri(BASE_URI)
                    .sessionId(sessionID)
                .when()
                    .get("/share/page/")
                .then().statusCode(200).extract().response();
        } catch (java.lang.AssertionError e) {
            System.out.println("Authorization: " + e.getMessage());
        }
    }

    /**
     * Проверка авторизации с правильными параметрами входа
     */
    @Test(enabled = false)
    public void test_checkAuthorizationWithValidParam() {
        String sessionID = getSessionID();

        Response responsePage = authorization(sessionID, ParametersForAutorisation.getValidParametersForAuthorization());
        if (!checkHttpResponce(responsePage, "Ошибка авторизации"))
            return;
    }

    /**
     * Проверка авторизации со неправильными параметрами входа
     * Возможно, тест не нужен - при неправильных данных сессию не получим
     */
    @Test(enabled = false)
    public void test_checkAuthorizationWithInvalidParam () {
        String sessionID = getSessionID();
    }
}

