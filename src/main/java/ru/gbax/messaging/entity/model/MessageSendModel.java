package ru.gbax.messaging.entity.model;

/**
 * Модель для отправки сообщений
 */
public class MessageSendModel {

    private Long toId;

    private String subject;

    private String body;

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
