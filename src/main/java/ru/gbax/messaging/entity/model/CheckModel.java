package ru.gbax.messaging.entity.model;

/**
 * Модель для возврата разультата
 */
public class CheckModel {

    private Boolean success;

    private String message;

    public CheckModel(Boolean success) {
        this.success = success;
    }

    public CheckModel(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
