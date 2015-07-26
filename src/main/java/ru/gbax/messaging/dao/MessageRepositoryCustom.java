package ru.gbax.messaging.dao;

import ru.gbax.messaging.entity.Message;
import ru.gbax.messaging.entity.User;
import ru.gbax.messaging.entity.model.MessageSortModel;

import java.util.List;

/**
 * Created by GBAX on 26.07.2015.
 */
public interface MessageRepositoryCustom {

    List<Message> findByToUser(User user, MessageSortModel messageSortModel);

}
