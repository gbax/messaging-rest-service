package ru.gbax.messaging.entity.model;

/**
 * Модель для регистрации пользователя
 */
public class RegistrationDataModel {

    private String login;
    private String password;
    private String repPassword;
    private String name;
    private String email;

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

    public String getRepPassword() {
        return repPassword;
    }

    public void setRepPassword(String repPassword) {
        this.repPassword = repPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "RegistrationDataModel{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", repPassword='" + repPassword + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
