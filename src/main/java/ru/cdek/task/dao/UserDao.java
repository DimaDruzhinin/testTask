package ru.cdek.task.dao;

import ru.cdek.task.entity.User;

import java.util.List;

public interface UserDao {

    boolean createUser(User user);

    List<User> getAll();

    User getUserById(long id);

}
