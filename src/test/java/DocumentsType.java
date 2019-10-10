import static java.util.Objects.isNull;
import static Utils.Utils.checkHttpResponce;
import static io.restassured.RestAssured.given;

import java.util.*;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import ClassesPOJO.DocumentTypeData;
import ClassesPOJO.DocumentTypeItem;
import ClassesPOJO.DocumentTypeListData;
import ParametersForTests.ParametersForDocumentsTypes;

/**
 * Класс для тестов работы с видами документов
 */
public class DocumentsType {
    /**
     * Получение списка типов документов
     */
    @Test//(enabled = true)
    public DocumentTypeListData test_getDocumentTypeList(){
        //Получить сессию
        String sessionID = API_ARM.getSessionID();

        Map<String, String> params = ParametersForDocumentsTypes.getParametersForAuthorization();
        Response responseAuthorisation = API_ARM.authorization(sessionID, params);
        if (! checkHttpResponce(responseAuthorisation, "Ошибка запроса getDictionary?dicName=Вид документа"))
            return null;

        //Получить данные для поиска видов документов GET /share/proxy/alfresco//lecm/dictionary/api/getDictionary?dicName=Вид документа
        String dicName = "Вид документа";
        String getDictionaryPath = "/share/page/proxy/alfresco/lecm/dictionary/api/getDictionary";

        Response responseGetDataForDocumentType = null;
        try {
            responseGetDataForDocumentType =
                given().baseUri(API_ARM.BASE_URI)
                    .sessionId(responseAuthorisation.sessionId())
                    .param("dicName", dicName)
                .when().get(getDictionaryPath)
                .then().extract().response();
        } catch(java.lang.AssertionError e){
            System.out.println("Get nodeRef: " + e.getMessage());
        }

        if (! checkHttpResponce(responseGetDataForDocumentType, "Ошибка запроса getDictionary?dicName=Вид документа"))
            return null;

        Gson gsonDicNameData = new Gson();
        if (isNull(responseGetDataForDocumentType.getBody())){
            System.out.println("Ответ на запрос списка типов документов не удалось преобразовать в jsonPath");
            return null;
        }

        //Распарсить тело ответа на основе  Pojo-класса DocumentTypeData (т.е. создать объект нужного типа)
        DocumentTypeData dicNameData = gsonDicNameData.fromJson(responseGetDataForDocumentType.getBody().asString(), DocumentTypeData.class);

        // Запросить список типов документов POST
        String documentTypeListPath = "/share/proxy/alfresco/lecm/search";

        //Подготовить тело запроса
        Map<String, Object> paramsDocumentTypeList = ParametersForDocumentsTypes.getParametersForDocumentTypeList();
        paramsDocumentTypeList.put("parent", dicNameData.nodeRef);
        paramsDocumentTypeList.put("itemType", dicNameData.itemType);

        Map<String, Object> paramsBody = new HashMap<>();
        paramsBody.put("params", paramsDocumentTypeList);

        Gson gson = new Gson();
        Response responseDocumentTypeList = null;
        try {
            responseDocumentTypeList =
                given().baseUri(API_ARM.BASE_URI)
                    .sessionId(responseAuthorisation.sessionId())
                    .contentType("application/json;charset=UTF-8")
                    .body(gson.toJson(paramsBody))
                .when()
                    .post(documentTypeListPath)
                .then().extract().response();
        } catch(java.lang.AssertionError e){
            System.out.println("Get documents types list error: "+ e.getMessage());
        }

        if (! checkHttpResponce(responseDocumentTypeList, "Ошибка запроса списка видов документов " + documentTypeListPath))
            return null;

        Gson gsonDicTypeList = Converters.registerDateTime(new GsonBuilder()).create();
        if (isNull(responseDocumentTypeList.getBody())){
            System.out.println("Ответ на запрос списка типов документов не удалось преобразовать в jsonPath");
            return null;
        }

        DocumentTypeListData documentTypeListData = gsonDicTypeList.fromJson(responseDocumentTypeList.getBody().asString(), DocumentTypeListData.class);

        ArrayList<DocumentTypeItem> documentTypeItems = documentTypeListData.getItems();
        List<DocumentTypeItem> documentTypes = documentTypeListData.getItems();
        System.out.println("*****************Список видов документов  из объекта ****************");
        documentTypes.forEach(a -> System.out.println(a.getItemDataProp_cm_nameValue()));

        return documentTypeListData;
    }
}

