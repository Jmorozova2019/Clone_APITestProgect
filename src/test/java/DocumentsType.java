import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;
import io.restassured.response.Response;

import static Utils.Utils.checkHttpResponce;
import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import ClassesPOJO.DocumentTypeData;
import ClassesPOJO.DocumentTypeItem;
import ClassesPOJO.DocumentTypeListData;
import ParametersForTests.ParametersForDocumentsTypes;

/**
 * Класс для тестов работы с видами документов
 */
public class DocumentsType {
    public static final String BASE_URI = "http://213.128.208.33:8080";

    /**
     * Получение списка типов документов
     */
    @Test(enabled = true)
    public void test_getDocumentTypeList(){
        //Получить сессию
        String sessionID = API_ARM.getSessionID();

        //Автроизоваться /share/page/dologin
        Map<String, String> params = ParametersForDocumentsTypes.getParametersForAuthorization();
        Response responseAuthorisation = API_ARM.authorization(sessionID, params);
        if (! checkHttpResponce(responseAuthorisation, "Ошибка запроса getDictionary?dicName=Вид документа"))
            return;

        /*Map<String, String> allCookies = responseAuthorisation.getCookies();
        System.out.println("----------Cookies--------------");
        allCookies.forEach((a, b) ->  System.out.println(a + ": " + b));*/

        //Получить данные для поиска видов документов GET /share/proxy/alfresco//lecm/dictionary/api/getDictionary?dicName=Вид документа
        String dicName = "Вид документа";
        String getDictionaryPath = "/share/proxy/alfresco//lecm/dictionary/api/getDictionary";

        Response responseGetDataForDocumentType = null;
        try {
            responseGetDataForDocumentType =
                given().baseUri(BASE_URI)
                    .sessionId(responseAuthorisation.getSessionId())//из авторизации
                    .cookie("alfLogin", "1570550237")
                    .cookie("alfUsername3", "Admin")
                    //.cookie("_alfTest", "_alfTest")
                    .param("dicName", dicName)
                .when().get(getDictionaryPath)
                .then().extract().response();
        } catch(java.lang.AssertionError e){
            System.out.println("Get nodeRef: "+ e.getMessage());
        }

        if (! checkHttpResponce(responseGetDataForDocumentType, "Ошибка запроса getDictionary?dicName=Вид документа"))
            return;
/*
        //if (responseGetDataForDocumentType.statusCode() == Status.SUCCESS)
        //Пока не получается добыть валидный объект(возвращается код 400), делаем получение данных из Json (исп. заглушка) - позже убрать
        JSONObject json = new JSONObject();
        json.put("title", "Вид документа");
        json.put("type", "lecm-dic:dictionary");
        json.put("nodeRef", "workspace://SpacesStore/7d3073fa-8688-455a-8568-64884b2cdf80");
        json.put("description", "Вид документа");
        json.put("itemType", "lecm-doc-dic-dt:typeDictionary");
        json.put("plane", "false");
        json.put("path", "\"\\/app:company_home\\/cm:Business_x0020_platform\\/cm:LECM\\/cm:\\u0421\\u0435\\u0440\\u0432\\u0438\\u0441_x0020_\\u0421\\u043f\\u0440\\u0430\\u0432\\u043e\\u0447\\u043d\\u0438\\u043a\\u0438\\/lecm-dic:\\u0412\\u0438\\u0434_x0020_\\u0434\\u043e\\u043a\\u0443\\u043c\\u0435\\u043d\\u0442\\u0430\"");
        //можно ли заменить на кириллицу для читаемости?
*/
        //Есть много способов распарсить - см. https://ru.stackoverflow.com/questions/745094/%D0%9A%D0%B0%D0%BA-%D0%B8-%D1%87%D0%B5%D0%BC-%D0%BF%D0%B0%D1%80%D1%81%D0%B8%D1%82%D1%8C-json-%D0%BD%D0%B0-java
        Gson gsonDicNameData = new Gson();
        DocumentTypeData dicNameData = gsonDicNameData.fromJson(responseGetDataForDocumentType.jsonPath().toString(), DocumentTypeData.class);
        //System.out.println(dicNameData.nodeRef);

        //Отбираем данные для следующего запроса - перенесла в сам запрос  ---------------------
        //String nodeRef = dicNameData.nodeRef;
        //String itemType = dicNameData.itemType;

        //Пока этот шаг пропускаем
        // Запрашиваем дерево типов документов GET /share/proxy/alfresco/lecm/dictionary/dictionary-tree?nodeRef=workspace%3A%2F%2FSpacesStore%2F7d3073fa-8688-455a-8568-64884b2cdf80
        /*Response responseTree = null;
        try {
            responseTree =
                    given().baseUri(BASE_URI)
                        .sessionId(sessionID)
                        .param("nodeRef", nodeRef)
                    .when()
                        .get("/share/proxy/alfresco/lecm/dictionary/dictionary-tree")
                    .then().extract().response();
        } catch(java.lang.AssertionError e){
            System.out.println("Authorization: "+ e.getMessage());
        }
        Gson gsonTreeData = new Gson();
        Type type = new TypeToken<ArrayList<DictionaryTreeItem>>(){}.getType();
        ArrayList<DictionaryTreeItem> treeItems = gsonTreeData.fromJson(responseTree.jsonPath().toString(), type);

        treeItems.forEach(a ->  System.out.println(a.title));
        */
        // Запросить список типов документов POST /share/proxy/alfresco/lecm/search
        String documentTypeListPath = "/share/proxy/alfresco/lecm/search";
        Response responseDocumentTypeList = null;
        Map<String, String> paramsDocumentTypeList = ParametersForDocumentsTypes.getParametersForDocumentTypeList();
        try {
            responseDocumentTypeList =
                    given().baseUri(BASE_URI)
                        .sessionId(sessionID)
                        .params(paramsDocumentTypeList)
                        .param("nodeRef", dicNameData.nodeRef)
                        .param("itemType", dicNameData.itemType)
                    .when()
                        .get(documentTypeListPath)
                    .then().extract().response();
        } catch(java.lang.AssertionError e){
            System.out.println("Get documents types list error: "+ e.getMessage());
        }

        if (! checkHttpResponce(responseGetDataForDocumentType, "Ошибка запроса списка видов документов /share/proxy/alfresco/lecm/search"))
            return;

        Gson gsonDicTypeList = new Gson();
        DocumentTypeListData documentTypeListData = gsonDicTypeList.fromJson(responseDocumentTypeList.jsonPath().toString(), DocumentTypeListData.class);

        ArrayList<DocumentTypeItem> documentTypeItems = documentTypeListData.getItems();
        System.out.println("*****************Список видов документов****************");
        documentTypeItems.forEach(a -> System.out.println(a.getItemDataProp_cm_nameValue()));
    }
}

//Работа с листом - пока не выбрасывать
/*
Gson gsonTreeData = new Gson();
Type type = new TypeToken<ArrayList<DictionaryTreeItem>>(){}.getType();
ArrayList<DictionaryTreeItem> treeItems = gsonTreeData.fromJson(responseDocumentTypeList.jsonPath().toString(), type);
treeItems.forEach(a ->  System.out.println(a.title));
*/

