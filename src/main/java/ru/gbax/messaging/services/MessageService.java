package ru.gbax.messaging.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gbax.messaging.dao.MessageRepository;
import ru.gbax.messaging.entity.Message;
import ru.gbax.messaging.entity.User;
import ru.gbax.messaging.entity.model.MessageModel;
import ru.gbax.messaging.entity.model.MessageListModel;
import ru.gbax.messaging.entity.model.MessageSendModel;
import ru.gbax.messaging.entity.model.MessageSortModel;
import ru.gbax.messaging.exceptions.ServiceErrorException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by GBAX on 26.07.2015.
 */
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Transactional
    public MessageListModel getUserMessages(MessageSortModel messageSortModel) throws ServiceErrorException {
        final User principal = securityService.getSecurityPrincipal();
        final String sort = messageSortModel.getSort();
        if (StringUtils.isNotEmpty(sort) && !Message.isSortFieldValid(sort)) {
            throw new ServiceErrorException("Представленное поле отсутствует в таблице");
        }
        final List<Message> messages = messageRepository.findByToUser(principal, messageSortModel);
        final boolean isAdmin = StringUtils.equals(principal.getRole(), User.ROLE_ADMIN);
        List<MessageModel> messageModels = new ArrayList<>();
        MessageListModel messageListModel = new MessageListModel(isAdmin, messageModels);
        if (messages.size() ==0) {
            return messageListModel;
        }
        for (Message message : messages) {
            MessageModel messageModel = getMessageModel(isAdmin, message);
            messageModels.add(messageModel);
        }
        return messageListModel;

    }

    private MessageModel getMessageModel(boolean isAdmin, Message message) {
        MessageModel messageModel = new MessageModel();
        messageModel.setId(message.getId());
        messageModel.setFrom(message.getAuthor().getName());
        if (isAdmin){
            messageModel.setTo(message.getToUser().getName());
        }
        messageModel.setSubject(message.getSubject());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        messageModel.setDate(dateFormat.format(message.getCreateDate()));
        return messageModel;
    }

    public void deleteMessage(Long id) throws ServiceErrorException {
        final User principal = securityService.getSecurityPrincipal();
        final List<Message> userMessage = messageRepository.findByIdAndToUser(id, principal);
        if (userMessage.size() != 1) {
            throw new ServiceErrorException("Сообщение не найдено");
        }
        messageRepository.delete(id);

    }


    public MessageModel getMessage(Long id) throws ServiceErrorException {
        final User securityPrincipal = securityService.getSecurityPrincipal();
        final List<Message> messageByIdAndUser = messageRepository.findMessageByIdAndUser(id, securityPrincipal);
        if (messageByIdAndUser.size() != 1) {
            throw new ServiceErrorException("Сообщение не найдено");
        }
        final Message message = messageByIdAndUser.get(0);
        final MessageModel messageModel = getMessageModel(StringUtils.equals(securityPrincipal.getRole(), User.ROLE_ADMIN), message);
        messageModel.setBody(message.getBody());
        return messageModel;
    }

    public void send(MessageSendModel messageSendModel) throws ServiceErrorException {
        final User securityPrincipal = securityService.getSecurityPrincipal();
        final List<User> usersById = userService.getUsersById(messageSendModel.getToId());
        if (usersById.size() != 1) {
            throw new ServiceErrorException("Пользователь не найден");
        }
        Message message = new Message();
        message.setToUser(usersById.get(0));
        message.setAuthor(securityPrincipal);
        message.setCreateDate(new Date());
        message.setSubject(messageSendModel.getSubject());
        message.setBody(messageSendModel.getBody());
        messageRepository.save(message);
    }
}
