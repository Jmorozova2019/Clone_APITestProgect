package actors;

//Пока непонятно, как рационально использовать этот тип. Для запросов придется создавать объекты из хранимых данных, а затем вставлять в мапу
public class User {
    String login;
    String password;
    String sessionID;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
}