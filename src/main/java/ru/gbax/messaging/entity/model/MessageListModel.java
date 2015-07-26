package ru.gbax.messaging.entity.model;

import java.util.List;

/**
 * Created by GBAX on 26.07.2015.
 */
public class MessageListModel {

    private Boolean isAdmin;

    private List<MessageModel> messageModels;

    public MessageListModel(Boolean isAdmin, List<MessageModel> messageModels) {
        this.isAdmin = isAdmin;
        this.messageModels = messageModels;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<MessageModel> getMessageModels() {
        return messageModels;
    }

    public void setMessageModels(List<MessageModel> messageModels) {
        this.messageModels = messageModels;
    }
}
