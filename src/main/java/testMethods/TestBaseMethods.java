package testMethods;

import io.restassured.response.Response;
import utils.Utils;

/**
 * @author Жанна Морозова
 */
public abstract class TestBaseMethods {
    boolean validateHttpResponse (Response response){return Utils.checkHttpResponce(response);};
}
