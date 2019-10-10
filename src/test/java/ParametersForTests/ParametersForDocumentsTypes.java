package ParametersForTests;

import java.util.HashMap;
import java.util.Map;

public class ParametersForDocumentsTypes {

    public static Map<String, String> getParametersForAuthorization() {
        Map<String, String> params = new HashMap<>();
        //params.put("success", "/share/page/");
        //params.put("failure", "/share/page/?error=true");
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

    public static Map<String, Object> getParametersForDocumentTypeList() {
        Map<String, Object> params = new HashMap<>();
        params.put("searchNodes", "");
        params.put("maxResults", 20);
        params.put("fields", "cm_name");
        params.put("nameSubstituteStrings", "");
        params.put("showInactive", false);
        params.put("startIndex", 0);
        params.put("sort", "");
        params.put("searchConfig", "");
        params.put("useChildQuery", false);
        params.put("useFilterByOrg", false);
        params.put("useOnlyInSameOrg", false);

        return params;
    }
}