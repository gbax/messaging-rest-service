package ru.gbax.messaging.entity.model;

/**
 * Модель для изменения пароля
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
