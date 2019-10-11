package parameters.request.authorization;
import static java.util.Arrays.asList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class Authorization {

    public static Map<String, String> getValidParameters() {
        Map<String, String> params = new HashMap<>();
        params.put("username", "proninin");
        params.put("password", "12345");

        return params;
    }

    public static Map<String, String> getInvalidParameters() {
        Map<String, String> params =  new HashMap<>();
        params.put("username", "roninin");                      //можно заменить случайно сгенеренной строкой
        params.put("password", "2345");                         //можно заменить случайно сгенеренной строкой

        return params;
    }
}
