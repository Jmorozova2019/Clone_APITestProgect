package testMethods.referenceBooks;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;

import POJO.referenceBooks.documentKind.DocumentKindData;
import POJO.referenceBooks.documentKind.DocumentKindItem;
import POJO.referenceBooks.documentKind.DocumentKindListData;
import static parameters.request.referenceBooks.DocumentKinds.getParametersForDocumentTypeList;
import static testMethods.authorization.Authorization.authorization;

public class DocumentKind {

    public static List<String> LOCAL_URL = asList("/share/page/proxy/alfresco/lecm/dictionary/api/getDictionary",
                                                  "/share/proxy/alfresco/lecm/search");
    /**
     * Получение списка типов документов
     */
    public static DocumentKindListData getDocumentTypeList(String BASE_URI){
        Response responseAuthorisation = authorization(BASE_URI, true);
        //if (! checkHttpResponce(responseAuthorisation, "Ошибка авторизации"))
        //    return null;

        //Получить данные для поиска видов документов GET /share/proxy/alfresco//lecm/dictionary/api/getDictionary?dicName=Вид документа
        String dicName = "Вид документа";//желательно вынести
        Response responseGetDataForDocumentType = null;
        try {
            responseGetDataForDocumentType =
                given().baseUri(BASE_URI)
                    .sessionId(responseAuthorisation.sessionId())
                    .param("dicName", dicName)
                .when().get(LOCAL_URL.get(0))
                .then().extract().response();
        } catch(java.lang.AssertionError e){
            System.out.println("Get nodeRef: " + e.getMessage());
        }

        //if (! checkHttpResponce(responseGetDataForDocumentType, "Ошибка запроса getDictionary?dicName=Вид документа"))
        //    return null;

        Gson gsonDicNameData = new Gson();
        if (isNull(responseGetDataForDocumentType.getBody())){
            System.out.println("Ответ на запрос списка типов документов не удалось преобразовать в jsonPath");
            return null;
        }

        //Распарсить тело ответа на основе Pojo-класса DocumentTypeData
        DocumentKindData dicNameData = gsonDicNameData.fromJson(responseGetDataForDocumentType.getBody().asString(), DocumentKindData.class);

        // Запросить список типов документов
        Map<String, Object> paramsDocumentTypeList = getParametersForDocumentTypeList();
        paramsDocumentTypeList.put("parent", dicNameData.nodeRef);
        paramsDocumentTypeList.put("itemType", dicNameData.itemType);

        Map<String, Object> paramsBody = new HashMap<>();
        paramsBody.put("params", paramsDocumentTypeList);

        Gson gson = new Gson();
        Response responseDocumentTypeList = null;
        try {
            responseDocumentTypeList =
                    given().baseUri(BASE_URI)
                        .sessionId(responseAuthorisation.sessionId())
                        .contentType("application/json;charset=UTF-8")
                        .body(gson.toJson(paramsBody))
                    .when()
                        .post(LOCAL_URL.get(1))
                    .then().extract().response();
        } catch(java.lang.AssertionError e){
            System.out.println("Get documents types list error: "+ e.getMessage());
        }

        //if (! checkHttpResponce(responseDocumentTypeList, "Ошибка запроса списка видов документов " + LOCAL_URL.get(1)))
         //   return null;

        Gson gsonDicTypeList = Converters.registerDateTime(new GsonBuilder()).create();
        if (isNull(responseDocumentTypeList.getBody())){
            System.out.println("Ответ на запрос списка типов документов не удалось преобразовать в jsonPath");
            return null;
        }

        DocumentKindListData documentKindListData = gsonDicTypeList.fromJson(responseDocumentTypeList.getBody().asString(), DocumentKindListData.class);
        List<DocumentKindItem> documentTypes = documentKindListData.getItems();
        //System.out.println("Список видов документов  из объекта");//для отладки
        documentTypes.forEach(a -> System.out.println(a.getItemDataProp_cm_nameValue()));

        return documentKindListData;
    }

}
