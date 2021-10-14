package model;

import java.io.Serializable;

public class LoginHistory implements Serializable {
    private String usernameLog ;
    private String decentralization ;
    private String timeLogIn ;
    private String timeLogOut ;

    public LoginHistory(){

    }

    public LoginHistory(String usernameLog,String decentralization, String timeLogIn, String timeLogOut) {
        this.usernameLog = usernameLog;
        this.decentralization = decentralization ;
        this.timeLogIn = timeLogIn;
        this.timeLogOut = timeLogOut;
    }

    public String getUsernameLog() {
        return usernameLog;
    }

    public void setUsernameLog(String usernameLog) {
        this.usernameLog = usernameLog;
    }

    public String getDecentralization() {
        return decentralization;
    }

    public void setDecentralization(String decentralization) {
        this.decentralization = decentralization;
    }

    public String getTimeLogIn() {
        return timeLogIn;
    }

    public void setTimeLogIn(String timeLogIn) {
        this.timeLogIn = timeLogIn;
    }

    public String getTimeLogOut() {
        return timeLogOut;
    }

    public void setTimeLogOut(String timeLogOut) {
        this.timeLogOut = timeLogOut;
    }
}
