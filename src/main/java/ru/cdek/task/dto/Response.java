package ru.cdek.task.dto;

import ru.cdek.task.entity.User;
import ru.cdek.task.status.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, инкапсулирующий ответ клиенту.
 */
public class Response {

    /**
     * Статус выполнения операции.
     */
    private ResponseStatus status;

    /**
     * Список пользователей.
     */
    private List<User> users = new ArrayList<>();

    public Response(ResponseStatus status) {
        this.status = status;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public List<User> getUsers() {
        return users;
    }
}
