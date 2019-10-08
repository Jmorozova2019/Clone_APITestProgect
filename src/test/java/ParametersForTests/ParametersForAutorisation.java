package ParametersForTests;

import java.util.HashMap;
import java.util.Map;

public class ParametersForAutorisation {

    public static Map<String, String> getValidParametersForAuthorization() {
        Map<String, String> params = new HashMap<>();
        params.put("success", "/share/page/");
        params.put("failure", "/share/page/?error=true");
        params.put("username", "proninin");
        params.put("password", "12345");

        return params;
    }

    public static Map<String, String> getInvalidParametersForAuthorization() {
        Map<String, String> params =  new HashMap<>();
        params.put("success", "/share/page/");
        params.put("failure", "/share/page/?error=true");
        params.put("username", "roninin");//можно заменить случайно сгенеренной строкой
        params.put("password", "2345");//можно заменить случайно сгенеренной строкой

        return params;
    }

    public static  Map<String, String> getArmCodeForAuthorization() {
        Map<String, String> params =  new HashMap<>();
        params.put("code", "SED");

        return params;
    }








}

