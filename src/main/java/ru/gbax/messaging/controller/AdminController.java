package ru.gbax.messaging.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.gbax.messaging.entity.model.CheckModel;
import ru.gbax.messaging.entity.model.UserTableModel;
import ru.gbax.messaging.exceptions.ServiceErrorException;
import ru.gbax.messaging.services.UserService;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Created by GBAX on 26.07.2015.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

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

    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.GET)
    public String main() {
        return "admin";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<UserTableModel> getUserList() {
        return userService.getAdminUserList();
    }

    @RequestMapping(value = "/makeNotAdmin/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CheckModel makeNotAdmin(@PathVariable("id") Long id)throws ServiceErrorException {
        userService.makeNotAdmin(id);
        return new CheckModel(true);
    }


    @RequestMapping(value = "/makeAdmin/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CheckModel makeAdmin(@PathVariable("id") Long id)throws ServiceErrorException {
        userService.makeAdmin(id);
        return new CheckModel(true);
    }


    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) throws ServiceErrorException {
        userService.deleteUser(id);
    }

}
