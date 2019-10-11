package utils;

import static java.util.Objects.isNull;
import io.restassured.response.Response;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс содержит утилитные статические функции
 */

public class Utils {

    /**
     * Метод проверки статуса ответа на HTTP запрос
     * @param response Response
     * @return boolean
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

    /**
     * Метод, возвращает текущее время. Предназначен для отладки.
     * @return String
     */
    public static String nowTime(){
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return time.format(formatter);
    }
}
