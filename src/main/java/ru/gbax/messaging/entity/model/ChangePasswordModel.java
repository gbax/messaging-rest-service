package ru.gbax.messaging.entity.model;

/**
 * Created by GBAX on 26.07.2015.
 */
public class ChangePasswordModel {

    private String password;
    private String newPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
