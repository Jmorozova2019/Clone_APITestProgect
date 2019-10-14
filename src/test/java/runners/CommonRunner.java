package runners;

import POJO.referenceBooks.documentKind.DocumentKindListData;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testMethods.authorization.Authorization;
import testMethods.referenceBooks.DocumentKind;

import static utils.Utils.*;

/**
 * Класс - запускатель тестов
 */
public class CommonRunner {
    public static String BASE_URL = "http://213.128.208.33:8080";

    @Test(groups = {"authorization"}, enabled = true)
    public void test_Authorization_Valid() {
        System.out.println(nowTime() + "Авторизация пользователя с корректными учетными данными");

        Authorization authorization = new Authorization();
        Response response = authorization.authorization(BASE_URL, true);

        Assert.assertTrue(authorization.validateHttpResponse(response), "Ошибка авторизации");
    }

    @Test(groups = {"authorization"}, enabled = true)
    public void test_Authorization_Invalid() {
        System.out.println(nowTime() + "Авторизация пользователя с некорректными учетными данными");

        Authorization authorization = new Authorization();
        Response response = authorization.authorization(BASE_URL, false);

        Assert.assertFalse(authorization.validateHttpResponse(response), "Ожидается ошибка, но авторизация прошла успешно");
    }

    @Test(groups = {"authorization"}, enabled = true)
    public void test_Exit() {
        System.out.println(nowTime() + " ****НЕ РЕАЛИЗОВАН**** " + "Выход из системы");

        Authorization authorization = new Authorization();
        Response response = authorization.exit(BASE_URL);

        //Assert.assertTrue(authorization.validateHttpResponse(response), "При выходе из системы произошла ошибка");
    }

    @Test(groups = {"documentKind"}, enabled = true)
    public void test_getDocumentTypeList() {
        System.out.println(nowTime() + "Получение списка видов документа");

        DocumentKind documentKind = new DocumentKind();
        DocumentKindListData documentKindList = documentKind.getDocumentTypeList(BASE_URL);

        //Assert.assertTrue(documentKindList.equals(expectedDocumentKindList), "При получении списка видов документа произошла ошибка");
    }
}
