package ru.cdek.task.dto;

import ru.cdek.task.status.ResponseStatus;
import ru.cdek.task.entity.User;

import java.util.ArrayList;
import java.util.List;

public class Response {

    private ResponseStatus status;

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
