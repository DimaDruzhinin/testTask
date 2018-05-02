package ru.cdek.task.service;

import ru.cdek.task.dto.Response;
import ru.cdek.task.entity.User;

public interface UserService {

    Response save(User user);

    Response findAll();
}
