import ClassesPOJO.DictionaryTreeItem;
import ClassesPOJO.DictionaryTypeData;
import ParametersForTests.ParametersForDocumentsTypes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.internal.http.Status;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.util.Objects.isNull;

public class DocumentsType {
    public static final String BASE_URI = "http://213.128.208.33:8080";

    /**
     * Получение списка типов документов
     */
    @Test(enabled = true)
    public void test_getDocumentTypeList(){
        //Получить сессию и автроизоваться
        String sessionID = API_ARM.getSessionID();

        Map<String, String> params = ParametersForDocumentsTypes.getParametersForAuthorization();
        Response responseAuthorisation = API_ARM.authorization(sessionID, params);

        if (!(responseAuthorisation.getStatusCode() >= 100 || responseAuthorisation.getStatusCode() < 400)){//Status.SUCCESS
            System.out.println("Authorization error");
            return;
        }
        Map<String, String> allCookies = responseAuthorisation.getCookies();
        System.out.println("----------Cookies--------------");
        allCookies.forEach((a, b) ->  System.out.println(a + ": " + b));
        /*
        Получили
        JSESSIONID: A204028BF02AAF7A40ACABC35CB01BF7
        alfLogin: 1570550750
        alfUsername3: Admin
        */

        //Получить данные для поиска видов документов GET /share/proxy/alfresco//lecm/dictionary/api/getDictionary?dicName=Вид документа
        String dicName = "Вид документа";

        Response responseGetDataForDocumentType = null;//Вот это запрос не проходит!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        try {
            responseGetDataForDocumentType =
                    given().baseUri(BASE_URI)
                        .sessionId(responseAuthorisation.getSessionId())//из авторизации
                        .cookie("alfLogin", "1570550237")//куки - пока как статическая заглушка, позже брать из авторизации
                        .cookie("alfUsername3", "Admin")
                        .param("dicName", dicName)
                    .when().get( "/share/proxy/alfresco//lecm/dictionary/api/getDictionary")
                    .then().extract().response();
        } catch(java.lang.AssertionError e){
            System.out.println("Get nodeRef: "+ e.getMessage());
        }

        System.out.println("*******************************************");
        //if (status.Suss)...
        //Пока не получается добыть валидный объект(возвращается код 400), делаем получение данных из Json (исп. заглушка)
        JSONObject json = new JSONObject();
        json.put("title", "Вид документа");
        json.put("type", "lecm-dic:dictionary");
        json.put("nodeRef", "workspace://SpacesStore/7d3073fa-8688-455a-8568-64884b2cdf80");
        json.put("description", "Вид документа");
        json.put("itemType", "lecm-doc-dic-dt:typeDictionary");
        json.put("plane", "false");
        json.put("path", "\"\\/app:company_home\\/cm:Business_x0020_platform\\/cm:LECM\\/cm:\\u0421\\u0435\\u0440\\u0432\\u0438\\u0441_x0020_\\u0421\\u043f\\u0440\\u0430\\u0432\\u043e\\u0447\\u043d\\u0438\\u043a\\u0438\\/lecm-dic:\\u0412\\u0438\\u0434_x0020_\\u0434\\u043e\\u043a\\u0443\\u043c\\u0435\\u043d\\u0442\\u0430\"");
        //можно ли заменить на кириллицу для читаемости?

        //Есть много способов распарсить - см. https://ru.stackoverflow.com/questions/745094/%D0%9A%D0%B0%D0%BA-%D0%B8-%D1%87%D0%B5%D0%BC-%D0%BF%D0%B0%D1%80%D1%81%D0%B8%D1%82%D1%8C-json-%D0%BD%D0%B0-java
        Gson gsonDicNameData = new Gson();
        DictionaryTypeData dicNameData = gsonDicNameData.fromJson(responseGetDataForDocumentType.jsonPath().toString(), DictionaryTypeData.class);
        System.out.println(dicNameData.nodeRef);

        //Отбираем данные для следующего запроса -  ---------------------
        String nodeRef = dicNameData.nodeRef;

        //Запрашиваем дерево типов документов GET /share/proxy/alfresco/lecm/dictionary/dictionary-tree?nodeRef=workspace%3A%2F%2FSpacesStore%2F7d3073fa-8688-455a-8568-64884b2cdf80
        /*Response responseTree = null;
        try {
            responseTree =
                    given().baseUri(BASE_URI)
                        .sessionId(sessionID)
                        .param("nodeRef", nodeRef)
                    .when()
                        .get("/share/proxy/alfresco/lecm/dictionary/dictionary-tree")
                    .then().extract().response();//!!!!!!!!!!
        } catch(java.lang.AssertionError e){
            System.out.println("Authorization: "+ e.getMessage());
        }
        Gson gsonTreeData = new Gson();
        Type type = new TypeToken<ArrayList<DictionaryTreeItem>>(){}.getType();
        ArrayList<DictionaryTreeItem> treeItems = gsonTreeData.fromJson(responseTree.jsonPath().toString(), type);

        treeItems.forEach(a ->  System.out.println(a.title));
        */

    }
}



