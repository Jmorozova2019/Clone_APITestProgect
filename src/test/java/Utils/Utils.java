package Utils;

import io.restassured.response.Response;

import static java.util.Objects.isNull;

public class Utils {
    /**
     * Метод проверки статуса ответа на HTTP запрос
     * @param response Response
     * @return ret boolean
     * Если работать не будет, поменять индекс
     */
    //Найти пример использования Status.SUCCESS и переписать
    public static boolean checkHttpResponce(Response response, String message){
        if ( isNull(response) || (!(response.getStatusCode() >= 100 || response.getStatusCode() < 400))){
            System.out.println(message + ", метод вызван из " + Thread.currentThread().getStackTrace()[2].getMethodName());
            return false;
        }
        return true;
    }

    String getRandomString(){
        return "Не реализовано";
    }
}
