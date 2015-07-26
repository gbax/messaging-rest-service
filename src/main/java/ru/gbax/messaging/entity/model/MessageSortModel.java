package ru.gbax.messaging.entity.model;

/**
 * Модель для сортировки сообщений
 */
public class MessageSortModel {

    private String sort;
    private String order;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
