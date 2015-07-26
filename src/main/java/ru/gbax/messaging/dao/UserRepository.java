package ru.gbax.messaging.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gbax.messaging.entity.User;

import java.util.List;

/**
 *  Репозиторий для работы с пользователями
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByLogin(String login);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.users WHERE u.id = :id")
    List<User> findUserFetchUsers(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.id <> :id")
    List<User> findUsersIdNotEqual(@Param("id")Long id);

    List<User> findById(Long id);

}
