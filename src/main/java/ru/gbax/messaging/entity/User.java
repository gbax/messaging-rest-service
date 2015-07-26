package ru.gbax.messaging.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.*;

/**
 * Пользователи
 */
@Entity
@Table(name = "user")
@SequenceGenerator(name = "SEQ_USER_TABLE", sequenceName = "SEQ_USER_TABLE")
public class User {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_TABLE")
    private Long id;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String role;

    @OneToMany(mappedBy = "toUser", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Message> messagesToUser;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Message> messagesFromUser;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_list",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "Id")},
            inverseJoinColumns = {@JoinColumn(name = "to_user_id", referencedColumnName = "Id")})
    private Set<User> users = new HashSet<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Message> getMessagesToUser() {
        return messagesToUser;
    }

    public void setMessagesToUser(List<Message> messagesToUser) {
        this.messagesToUser = messagesToUser;
    }

    public List<Message> getMessagesFromUser() {
        return messagesFromUser;
    }

    public void setMessagesFromUser(List<Message> messagesFromUser) {
        this.messagesFromUser = messagesFromUser;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void removeUser(Long userId) {
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
            final User next = iterator.next();
            if (Objects.equals(next.getId(), userId)) {
                iterator.remove();
            }
        }

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(id, user.id)
                .append(login, user.login)
                .append(password, user.password)
                .append(name, user.name)
                .append(email, user.email)
                .append(role, user.role)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(login)
                .append(password)
                .append(name)
                .append(email)
                .append(role)
                .toHashCode();
    }
}
