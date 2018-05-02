package ru.cdek.task.dto;

/**
 * Фильтр запроса пользователей.
 */
public class UserFilter {

    /**
     * Минимальный идентификатор пользователя в хранилище.
     */
    private long minId;

    /**
     * Максимальный идентификатор пользователя в хранилище.
     */
    private long maxId;

    public UserFilter(long minId, long maxId) {
        this.minId = minId;
        this.maxId = maxId;
    }

    public long getMinId() {
        return minId;
    }

    public void setMinId(long minId) {
        this.minId = minId;
    }

    public long getMaxId() {
        return maxId;
    }

    public void setMaxId(long maxId) {
        this.maxId = maxId;
    }
}
