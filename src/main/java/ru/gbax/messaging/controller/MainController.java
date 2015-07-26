package ru.gbax.messaging.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gbax.messaging.exceptions.ServiceErrorException;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Главный контроллер
 */
@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

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

    /**
     * Отображение начальной страницы
     *
     * @return начальная страница
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        return "redirect:/messages";
    }

    /**
     * @return страница логина
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/index";
        }
        return "login";
    }

    /**
     * @return страница регистрации
     */
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {
        return "registration";
    }

}
