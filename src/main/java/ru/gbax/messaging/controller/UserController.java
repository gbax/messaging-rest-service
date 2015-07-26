package ru.gbax.messaging.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.gbax.messaging.entity.User;
import ru.gbax.messaging.entity.model.ChangePasswordModel;
import ru.gbax.messaging.entity.model.CheckModel;
import ru.gbax.messaging.entity.model.RegistrationDataModel;
import ru.gbax.messaging.entity.model.UserModel;
import ru.gbax.messaging.exceptions.ServiceErrorException;
import ru.gbax.messaging.utils.AuthenticationProvider;
import ru.gbax.messaging.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Контроллер для работы с пользователями
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

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


    @RequestMapping(value = "/checkDuplicate/{login}", method = RequestMethod.GET)
    @ResponseBody
    public CheckModel checkDuplicate(@PathVariable("login") String login) {
        final List<User> usersByLogin = userService.getUsersByLogin(login);
        return new CheckModel(usersByLogin.size() > 0);
    }



    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    @ResponseBody
    public List<UserModel> getUsers() {
        return userService.getUsers();
    }


    @RequestMapping(value = "/getUsersNotInList", method = RequestMethod.GET)
    @ResponseBody
    public List<UserModel> getUsersNotInList() {
        return userService.getUsersNotInList();
    }


    @RequestMapping(value = "/addNew/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void addNew(@PathVariable("id")Long id) {
        userService.addNew(id);
    }



    @RequestMapping(value = "/deleteFromList/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void deleteFromList(@PathVariable("id") Long id) {
        userService.deleteFromList(id);
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CheckModel register(@RequestBody RegistrationDataModel dataModel, HttpServletRequest request) throws ServiceErrorException {
        userService.createUser(dataModel);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dataModel.getLogin(), dataModel.getPassword());
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = this.authenticationProvider.authenticate(token);
        logger.debug("Logging in with [{}]", authentication.getPrincipal());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new CheckModel(true);
    }

    @RequestMapping(value = "/registerFromAdmin", method = RequestMethod.POST)
    @ResponseBody
    public CheckModel registerFromAdmin(@RequestBody RegistrationDataModel dataModel) throws ServiceErrorException {
        userService.createUser(dataModel);
        return new CheckModel(true);
    }


    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public CheckModel changePassword(@RequestBody ChangePasswordModel changePasswordModel) throws ServiceErrorException {
        return userService.changePassword(changePasswordModel);
    }

}
