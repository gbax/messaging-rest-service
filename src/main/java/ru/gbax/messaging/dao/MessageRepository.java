package ru.gbax.messaging.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gbax.messaging.entity.Message;
import ru.gbax.messaging.entity.User;

import java.util.List;

/**
 * Репозиторий для работы с сообщениями
 */
@Repository
public interface MessageRepository extends CrudRepository<Message, Long>, MessageRepositoryCustom {

    List<Message> findByIdAndToUser(Long id, User user);

    @Query("SELECT m FROM Message m JOIN FETCH m.author JOIN FETCH m.toUser WHERE m.id = :id AND m.toUser = :user")
    List<Message> findMessageByIdAndUser(@Param("id") Long id, @Param("user") User user);
}
