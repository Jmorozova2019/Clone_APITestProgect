package ParametersForTests;

import java.util.HashMap;
import java.util.Map;

public class ParametersForDocumentsTypes {

    public static Map<String, String> getParametersForAuthorization() {
        Map<String, String> params = new HashMap<>();
        params.put("success", "/share/page/");
        params.put("failure", "/share/page/?error=true");
        params.put("username", "Admin");
        params.put("password", "Kl%72Jrc12m!!");

        return params;
    }

    //
    public static Map<String, String> getParametersForGetDocumentType() {
        Map<String, String> params = new HashMap<>();
        params.put("searchNodes", "");
        params.put("maxResults", "20");//?
        params.put("fields", "cm:name");
        params.put("nameSubstituteStrings", "");
        params.put("showInactive", "false");
        params.put("startIndex", "0");
        params.put("sort", "");
        params.put("filter", "[]");
        params.put("useChildQuery", "false");
        params.put("useFilterByOrg", "false");
        params.put("useOnlyInSameOrg", "false");

        return params;
    }

        /*
        //Первые три параметра нужно получить заранее - из запроса  GET /share/proxy/alfresco//lecm/dictionary/api/getDictionary?dicName=Вид документа
        "parent": "workspace://SpacesStore/7d3073fa-8688-455a-8568-64884b2cdf80", -
        "itemType": "lecm-doc-dic-dt:typeDictionary",
        "searchConfig": "{\"formData\":\"{\\\"datatype\\\":\\\"lecm-doc-dic-dt:typeDictionary\\\"}\"}",

        //эти можно заполнить сразу
        "searchNodes": "",
        "maxResults": 20,
        "fields": "cm_name",
        "nameSubstituteStrings": "",

        "showInactive": false,
        "startIndex": 0,
        "sort": "",
        "filter": [],

        "useChildQuery": false,
        "useFilterByOrg": false,
        "useOnlyInSameOrg": false
        */
}