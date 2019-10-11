package runners;

import POJO.referenceBooks.documentKind.DocumentKindListData;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static testMethods.authorization.Authorization.*;
import static testMethods.referenceBooks.DocumentKind.*;
import static utils.Utils.*;

/**
 * Класс - запускатель тестов
 */

public class CommonRunner {
    public static String BASE_URL = "http://213.128.208.33:8080";

    /**
     * 1 Требования для внутренней функции
     * 1.1 Если ответ на запрос не содержит (не должен содержать) body, возвращаемое значение - Response
     * 1.2 Если ответ на запрос должен содержать body, возвращаемое значение - объект, сформированиый из body на основе соответствующего POJO
     * 2.1 Название теста должно формироваться по правилу test_имяВнутреннейФункции
     * 2.2 Если вызывается одна и та же внутренняя функция с различными параметрами, добавлять поясняющий постфикс
     * 3 Структура тестов в этом пакете должна быть одинаковой для всех тестов без исключения
     * 3.1. Отладочное сообщение (опционально) - текст сообщения дожен совпадать с названием теста
     * 3.2. Сбор и подготовка параметров
     * 3.3. Выполнение теста - вызов соответствующей функции
     * 3.4. Проверки (asserts)
     *      Если ответ на запрос не содержит (не должен содержать) body - специфичные для запроса проверки (например,
     *      для авторизации (и успешной, и неуспешной, возвращается код 302 - нужно проверять наличие 2 новых set-cookies)
     *      Если ответ на запрос должен содержать body - в блоке проверок сравнивать этот объект с ОЖИДАЕМЫМ. Сравнивать не все поля, а только нужные (в equals).
     *      Assert.assertTrue(Object1.equals(Object2));
     *      Все внутренние функции ПРОВЕРКИ должны возвращать boolean
     */

    //НЕ ДОДЕЛАНО - реализовать специфичные проверки авторизации
    @Test(groups = {"authorization"}, enabled = true)
    public void test_Authorization_Valid() {
        System.out.println(nowTime() + "Авторизация пользователя с корректными учетными данными");

        Response response = authorization(BASE_URL, true);
        //Assert.assertTrue(authorization.checkResponce(response), "Ошибка авторизации");
    }

    @Test(groups = {"authorization"}, enabled = true)
    public void test_Authorization_Invalid() {
        System.out.println(nowTime() + "Авторизация пользователя с не корректными учетными данными");

        Response response = authorization(BASE_URL, false);
        //Assert.assertFalse(checkHttpResponce(response, "Ожидается ошибка, но авторизация прошла успешно"));
    }

    @Test(groups = {"authorization"}, enabled = true)
    public void test_Exit() {
        System.out.println(nowTime() + " ****НЕ РЕАЛИЗОВАН**** " + "Выход из системы");

        Response response = exit(BASE_URL);
        //Assert.assertTrue(checkHttpResponce(response, "При выходе из системы произошла ошибка"));
    }

    @Test(groups = {"documentKind"}, enabled = true)
    public void test_getDocumentTypeList() {
        System.out.println(nowTime() + "Получение списка видов документа");

        DocumentKindListData documentKindList = getDocumentTypeList(BASE_URL);
        //Assert.assertTrue(documentKindList.equals(expectedDocumentKindList), "При получении списка видов документа произошла ошибка");
    }
}
