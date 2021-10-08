package model;

import java.io.Serializable;

public class Account implements Serializable {
    private String ownerId;
    private String userName;
    private String password;
    private String decentralization;


    public Account() {
    }



    public Account(String ownerId, String userName, String password, String decentralization) {
        this.userName = userName;
        this.password = password;
        this.decentralization = decentralization;
        this.ownerId = ownerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDecentralization() {
        return decentralization;
    }

    public void setDecentralization(String decentralization) {
        this.decentralization = decentralization;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
