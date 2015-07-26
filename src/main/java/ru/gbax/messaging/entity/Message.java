package ru.gbax.messaging.entity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Сообщение
 */
@Entity
@Table(name = "message")
@SequenceGenerator(name = "SEQ_MESS_TABLE", sequenceName = "SEQ_MESS_TABLE")
public class Message {

    private static final List<String> SORT_FIELDS = Arrays.asList("createDate","subject","author","toUser");

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MESS_TABLE")
    private Long id;

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column
    private String subject;

    @Column
    private String body;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "author_user_id")
    private User author;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User toUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public static Boolean isSortFieldValid(String field) {
        return SORT_FIELDS.contains(field);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", author=" + author +
                ", toUser=" + toUser +
                '}';
    }
}
