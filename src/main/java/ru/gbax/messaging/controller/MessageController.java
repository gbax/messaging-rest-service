package ru.gbax.messaging.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.gbax.messaging.entity.model.MessageListModel;
import ru.gbax.messaging.entity.model.MessageModel;
import ru.gbax.messaging.entity.model.MessageSendModel;
import ru.gbax.messaging.entity.model.MessageSortModel;
import ru.gbax.messaging.exceptions.ServiceErrorException;
import ru.gbax.messaging.services.MessageService;

import java.io.IOException;
import java.io.Writer;


/**
 * Контроллер для работы с сообщениями
 */
@Controller
@RequestMapping(value = "/messages")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    /**
     * Обработка ошибки на сервере
     *
     * @param e      исключение ServiceErrorException
     * @param writer поток для вывода сообщения
     * @throws IOException
     */
    @ExceptionHandler(ServiceErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public void handleException(final ServiceErrorException e, Writer writer) throws IOException {
        logger.error(e.getMessage(), e);
        writer.write(String.format("{\"error\":\"%s\"}", e.getMessage()));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String main() {
        return "messages";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public MessageListModel getUserMessages(@RequestBody MessageSortModel messageSortModel) throws ServiceErrorException {
        return messageService.getUserMessages(messageSortModel);
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void send(@RequestBody MessageSendModel messageSendModel) throws ServiceErrorException {
        messageService.send(messageSendModel);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteFromList(@PathVariable("id") Long id) throws ServiceErrorException {
        messageService.deleteMessage(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public MessageModel message(@PathVariable("id") Long id) throws ServiceErrorException {
        return messageService.getMessage(id);
    }

}
