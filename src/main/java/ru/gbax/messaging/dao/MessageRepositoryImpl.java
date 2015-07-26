package ru.gbax.messaging.dao;

import org.apache.commons.lang3.StringUtils;
import ru.gbax.messaging.entity.Message;
import ru.gbax.messaging.entity.User;
import ru.gbax.messaging.entity.model.MessageSortModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Расширение для репозитория сообщений
 */
public class MessageRepositoryImpl implements MessageRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Message> findByToUser(User user, MessageSortModel messageSortModel) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> q = cb.createQuery(Message.class);
        Root<Message> root = q.from(Message.class);
        root.fetch("toUser");
        root.fetch("author");
        q.select(root).where(cb.equal(root.get("toUser"), user));
        if (StringUtils.equalsIgnoreCase(messageSortModel.getOrder(), "asc")) {
            q.orderBy(cb.asc(root.get(messageSortModel.getSort())));
        } else if (StringUtils.equalsIgnoreCase(messageSortModel.getOrder(), "desc")) {
            q.orderBy(cb.desc(root.get(messageSortModel.getSort())));
        }
        return em.createQuery(q).getResultList();
    }

}
