import ParametersForTests.ParametersForAutorisation;
import io.restassured.http.Headers;
import io.restassured.internal.path.xml.NodeImpl;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static java.util.Objects.isNull;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import junit.framework.Assert;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Не сделано толком логирование и не исправлены тестовые сообщения
 */
public class API_ARM {
    //protected static Logger logger = LogManager.getLogger(API_ARM.class.getName());
    //logger.log(Level.WARNING);

    private static final String BASE_URI = "http://213.128.208.33:8080";
    //public String SESSION_ID; // выносить в глобальную переменную не стоит - будут проблемы при распараллеливании

    /**
     * Получение ID сессии (аутентификация)
     */
    public String getSessionID(Map<String, Object> paramsReqMap){
        String sessionID = null;
        Response response = null;
        try {
            response =
                    given()
                            .params(paramsReqMap)
                    .when()
                        .post(BASE_URI)
                    .then().statusCode(200).extract().response();
        } catch(java.lang.AssertionError e){
            System.out.println("Authorization: "+ e.getMessage());
        }

        if (isNull(response)) {
            System.out.println("Response is null, sessionId not found");
            return null;
        }
        sessionID = response.getSessionId();
        System.out.println(sessionID);
        return sessionID;
     }

    /**
     * Проверка, что при валидных данных возвращается sessionID
     */
    @Test(enabled = false)
    public void test_checkGetSessionID(){
        String sessionID = getSessionID(ParametersForAutorisation.getValidParametersForAuthorization());
        Assert.assertFalse("SessionID not found", sessionID.isEmpty());

        //+ добавить выход - создать группу в которой нужен AfterTest
    }

    /**
     * Проверка формы авторизации (в сценарии также требуется проверка эламентов - не реализовано)
     * Можно отключать - он будет совпадать с тестом test_checkAuthorizationWithValidParam
     * или test_checkAuthorizationWithInvalidParam
     */
    @Test(enabled = true)
    public void test_checkAuthorizationWindowItems() {
        String sessionID = getSessionID(ParametersForAutorisation.getValidParametersForAuthorization());
        if (isNull(sessionID)) {
            System.out.println("SessionID is null, no autorisation");
            return;
        }

        Response responsePage = null;
        try {
            responsePage =
                    given().baseUri(BASE_URI)
                            .sessionId(sessionID)
                            //.params(ParametersForAutorisation.getArmCodeForAuthorization())
                    .when()
                            .get("/share/page/")
                    .then().statusCode(200).extract().response();
        } catch(java.lang.AssertionError e){
            System.out.println("Authorization: "+ e.getMessage());
        }

        XmlPath xmlPathPage = new XmlPath(XmlPath.CompatibilityMode.HTML, responsePage.asString());
        //xmlPathPage.getString("html.body.div[3].div.form.div[1].input");//Поле ввода Имя пользователя - нужно проверить на содержимое пока непонятно как
        String lblUserName = xmlPathPage.getString("html.body.div[3].div.form.div[1].label");//лейбл Имя пользователя
        Assert.assertTrue("Ожидаемое название поля Имя пользователя, получено " + lblUserName, lblUserName.equals("Имя пользователя"));

        String lblPassword = xmlPathPage.getString("/html/body/div[3]/div/form/div[2]/label");//лейбл Пароль
        Assert.assertTrue("Ожидаемое название поля Пароль, получено " + lblPassword, lblPassword.equals("Пароль"));

        String btnEnter = xmlPathPage.getString("html.body.div[3].div.form.div[3].span.span.button");//лейбл Пароль
        Assert.assertTrue("Ожидаемое название кнопки Войти, получено " + btnEnter, btnEnter.equals("Пароль"));
    }

    /**
     * Проверка авторизации с правильными параметрами входа
     */
    @Test(enabled = false)
    public void test_checkAuthorizationWithValidParam(){
        String sessionID = getSessionID(ParametersForAutorisation.getValidParametersForAuthorization());
        if(isNull(sessionID)) {
            System.out.println("SessionID is null, no autorisation");
            return;
        }

       Response responsePage = null;
        try {
            responsePage =
                    given().baseUri(BASE_URI)
                        .sessionId(sessionID)
                        .params(ParametersForAutorisation.getArmCodeForAuthorization())
                    .when()
                        .get("/share/page/")
                    .then().statusCode(200).extract().response();
        } catch(java.lang.AssertionError e){
            System.out.println("Authorization: "+ e.getMessage());
        }

        //if (isNull(responsePage))...
        XmlPath xmlPathPage = new XmlPath(XmlPath.CompatibilityMode.HTML, responsePage.asString());
        String xmlPathPageStr = xmlPathPage.getString("html.head.title");
        System.out.println("--------------------" + xmlPathPageStr + "-------------") ;

        //-----------------------
        Map<String, Object> params = ParametersForAutorisation.getArmCodeForAuthorization();
        Response responseArmCode = null;
        try {
            responseArmCode =
                    given().baseUri(BASE_URI)
                            .sessionId(sessionID)
                            .params(ParametersForAutorisation.getArmCodeForAuthorization())
                            .when()
                            .get("/share/page/arm")
                            .then().statusCode(200).extract().response();
        } catch(java.lang.AssertionError e){
            System.out.println("Authorization: "+ e.getMessage());
        }
        //Приходит ответ в виде
        XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, responseArmCode.asString());
        String v = xmlPath.getString("html.head.title");
        System.out.println("--------------------" + v + "-------------") ;

    }

    /**
     * Проверка авторизации со неправильными параметрами входа
     * Возможно, тест не нужен - при неправильных данных сессию не получим
     */
    @Test(enabled = false)
    public void test_checkAuthorizationWithInvalidParam(){
        String sessionID = getSessionID(ParametersForAutorisation.getValidParametersForAuthorization());
        if(isNull(sessionID)) {
            System.out.println("SessionID is null, no authorization");
            return;
        }
    }
}
