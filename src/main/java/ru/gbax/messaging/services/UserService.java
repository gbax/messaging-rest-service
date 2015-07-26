package ru.gbax.messaging.services;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gbax.messaging.dao.UserRepository;
import ru.gbax.messaging.entity.User;
import ru.gbax.messaging.entity.model.*;
import ru.gbax.messaging.exceptions.ServiceErrorException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Сервис для работы с данными пользователя
 */
@Service
@Transactional
public class UserService {

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    public List<User> getUsersByLogin(final String login) {
        return userRepository.findByLogin(login);
    }

    public List<User> getUsersById(final Long id) {
        return userRepository.findById(id);
    }


    public void createUser(final RegistrationDataModel model) {
        User user = new User();
        user.setLogin(model.getLogin());
        user.setPassword(model.getPassword());
        user.setEmail(model.getEmail());
        user.setName(model.getName());
        user.setRole(User.ROLE_USER);
        userRepository.save(user);

    }

    @Transactional
    public List<UserModel> getUsers() {
        final User securityPrincipal = securityService.getSecurityPrincipal();
        final List<User> users = userRepository.findUserFetchUsers(securityPrincipal.getId());
        List<UserModel> userModels = new ArrayList<>();
        if (users.size() != 1) {
            return userModels;
        }
        for (User user : users.get(0).getUsers()) {
            userModels.add(new UserModel(user.getId(), user.getName()));
        }
        return userModels;
    }

    @Transactional
    public List<UserModel> getUsersNotInList() {
        final User securityPrincipal = securityService.getSecurityPrincipal();
        final List<User> users = userRepository.findUserFetchUsers(securityPrincipal.getId());
        final ArrayList<User> allUsers = Lists.newArrayList(userRepository.findUsersIdNotEqual(securityPrincipal.getId()));
        List<UserModel> userModels = new ArrayList<>();
        if (users.size() == 0) {
            return userModels;
        }
        final Collection<User> subtract = CollectionUtils.subtract(allUsers, users.get(0).getUsers());
        if (subtract.size() == 0) {
            return userModels;
        }
        for (User user : subtract) {
            userModels.add(new UserModel(user.getId(), user.getName()));
        }
        return userModels;
    }

    public void addNew(Long id) {
        final User securityPrincipal = securityService.getSecurityPrincipal();
        final List<User> users = userRepository.findUserFetchUsers(securityPrincipal.getId());
        final List<User> byId = userRepository.findById(id);
        if (users.size() == 1 && byId.size() == 1) {
            final User user = users.get(0);
            user.addUser(byId.get(0));
            userRepository.save(user);
        }
    }

    public void deleteFromList(Long id) {
        final User securityPrincipal = securityService.getSecurityPrincipal();
        final List<User> users = userRepository.findUserFetchUsers(securityPrincipal.getId());
        final List<User> byId = userRepository.findById(id);
        if (users.size() == 1 && byId.size() == 1) {
            final User user = users.get(0);
            user.removeUser(byId.get(0));
            userRepository.save(user);
        }
    }

    public CheckModel changePassword(ChangePasswordModel changePasswordModel) {
        User securityPrincipal = securityService.getSecurityPrincipal();
        User user = userRepository.findById(securityPrincipal.getId()).get(0);
        if (!StringUtils.equals(changePasswordModel.getPassword(), user.getPassword())) {
            return new CheckModel(false, "Текущий пароль указан неверно");
        }
        if (StringUtils.isEmpty(changePasswordModel.getPassword())) {
            return new CheckModel(false, "Новый пароль не указан");
        }
        user.setPassword(changePasswordModel.getNewPassword());
        userRepository.save(user);
        return new CheckModel(true);
    }

    public List<UserTableModel> getAdminUserList() {
        User securityPrincipal = securityService.getSecurityPrincipal();
        final List<User> users = userRepository.findUsersIdNotEqual(securityPrincipal.getId());
        List<UserTableModel> tableModels = new ArrayList<>();
        for (User user : users) {
            UserTableModel userTableModel = new UserTableModel();
            if (StringUtils.equals(user.getRole(), User.ROLE_ADMIN)) {
                userTableModel.setAdmin(true);
            }
            userTableModel.setEmail(user.getEmail());
            userTableModel.setLogin(user.getLogin());
            userTableModel.setRole(user.getRole());
            userTableModel.setName(user.getName());
            userTableModel.setId(user.getId());
            tableModels.add(userTableModel);
        }
        return tableModels;
    }

    public void deleteUser(Long id) throws ServiceErrorException {
        List<User> users = userRepository.findUserFetchUsers(id);
        if (users.size() != 1) {
            throw new ServiceErrorException("Пользователь не найден");
        }
        final User user = users.get(0);
        for(User userInner : user.getUsers()){
            List<User> innerUsers = userRepository.findUserFetchUsers(userInner.getId());
            if (innerUsers.size() == 1) {
                innerUsers.get(0).getUsers().remove(user);
                userRepository.save(innerUsers.get(0));
            }
        }
        userRepository.delete(user);
    }

    public void makeNotAdmin(Long id) throws ServiceErrorException {
        List<User> users = userRepository.findUserFetchUsers(id);
        if (users.size() != 1) {
            throw new ServiceErrorException("Пользователь не найден");
        }
        final User user = users.get(0);
        user.setRole(User.ROLE_USER);
        userRepository.save(user);
    }

    public void makeAdmin(Long id) throws ServiceErrorException {
        List<User> users = userRepository.findUserFetchUsers(id);
        if (users.size() != 1) {
            throw new ServiceErrorException("Пользователь не найден");
        }
        final User user = users.get(0);
        user.setRole(User.ROLE_ADMIN);
        userRepository.save(user);
    }
}
