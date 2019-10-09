import java.util.Map;
import static java.util.Objects.isNull;
import io.restassured.internal.path.xml.NodeChildrenImpl;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;
import org.testng.Assert;
import ParametersForTests.ParametersForAutorisation;

/**
 * Не сделано толком логирование и не исправлены тестовые сообщения
 */
public class API_ARM {
    //protected static Logger logger = LogManager.getLogger(API_ARM.class.getName());
    //logger.log(Level.WARNING);

    public static final String BASE_URI = "http://213.128.208.33:8080";

    ///*****************************************************************************************************************
    // Начало Блока переиспользуемых функций
    //******************************************************************************************************************

    /**
     * Получение ID сессии
     */
    public static String getSessionID(){
        String sessionID = null;
        Response response = null;
        try {
            response =
                    given().baseUri(BASE_URI)
                            .when()
                    .get()
                    .then().statusCode(200).extract().response();
        } catch(java.lang.AssertionError e){
            System.out.println("Getting session: "+ e.getMessage());
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
                            //.get("/share/page/")
                            .post("/share/page/dologin")
                    .then().extract().response();//statusCode(200).
        } catch (java.lang.AssertionError e) {
            System.out.println("Authorization: " + e.getMessage());
            return null;
        }
        return response;
    }

    //*****************************************************************************************************************
    // Конец Блока переиспользуемых функций
    //******************************************************************************************************************
    /**
     * Проверка, что возвращается sessionID (никаких данных для этого не нужно - только путь)
     */
    @Test(enabled = false)
    public void test_checkGetSessionID(){
        String sessionID = getSessionID();
        Assert.assertFalse(sessionID.isEmpty(), "SessionID not found");

        //+ добавить выход - создать группу в которой нужен AfterTest
    }

    /**
     * Проверка формы авторизации (в сценарии также требуется проверка эламентов - не реализовано)
     * Можно отключать - он будет совпадать с тестом test_checkAuthorizationWithValidParam
     * или test_checkAuthorizationWithInvalidParam
     */
    @Test(enabled = true)
    public void test_checkAuthorizationWindowItems() {
        String sessionID = getSessionID();
        if (isNull(sessionID)) {
            System.out.println("SessionID is null, no authorization");
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

        //НЕ ОТЛАЖЕНО
        XmlPath xmlPathPage = new XmlPath(XmlPath.CompatibilityMode.HTML, responsePage.asString());
        //xmlPathPage.getString("html.body.div[3].div.form.div[1].input");//Поле ввода Имя пользователя - нужно проверить на содержимое пока непонятно как

        System.out.println("---------------------------");
        String xmlPathToItem = "html.body.div[3]";//html.body.div[3].div.form.div[1].label
        System.out.println("---------------------------");
        if (xmlPathPage.get(xmlPathToItem) instanceof NodeChildrenImpl) {
            if (((NodeChildrenImpl) xmlPathPage.get(xmlPathToItem)).size() == 0) {
                System.out.println("+++++++++++++++++");
            }
        }

        System.out.println(xmlPathPage.get(xmlPathToItem).toString());

        /*String lblUserName = xmlPathPage.getString(xmlPathToItem + ".text()");//лейбл Имя пользователя
        Assert.assertTrue("Ожидаемое название поля Имя пользователя, получено " + lblUserName, lblUserName.equals("Имя пользователя"));

        String lblPassword = xmlPathPage.getString("/html/body/div[3]/div/form/div[2]/label/text()[1]");//лейбл Пароль
        Assert.assertTrue("Ожидаемое название поля Пароль, получено " + lblPassword, lblPassword.equals("Пароль"));

        String btnEnter = xmlPathPage.getString("html/body/div[3]/div/form/div[3]/span/span/button/text()");//лейбл Пароль
        Assert.assertTrue("Ожидаемое название кнопки Войти, получено " + btnEnter, btnEnter.equals("Пароль"));*/
    }

    /**
     * Проверка авторизации с правильными параметрами входа
     */
    @Test(enabled = false)
    public void test_checkAuthorizationWithValidParam(){
        String sessionID = getSessionID();
        if(isNull(sessionID)) {
            System.out.println("SessionID is null, no autorisation");
            return;
        }

        Response responsePage = authorization(sessionID, ParametersForAutorisation.getValidParametersForAuthorization());
        //if (isNull(responsePage))...
        XmlPath xmlPathPage = new XmlPath(XmlPath.CompatibilityMode.HTML, responsePage.asString());
        String xmlPathPageStr = xmlPathPage.getString("html.head.title");
        System.out.println("--------------------" + xmlPathPageStr + "-------------") ;

        //-----------------------
        /*Map<String, Object> params = ParametersForAutorisation.getArmCodeForAuthorization();
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
        */
    }

    /**
     * Проверка авторизации со неправильными параметрами входа
     * Возможно, тест не нужен - при неправильных данных сессию не получим
     */
    @Test(enabled = false)
    public void test_checkAuthorizationWithInvalidParam(){
        String sessionID = getSessionID();
        if(isNull(sessionID)) {
            System.out.println("SessionID is null, no authorization");
            return;
        }
    }
}
