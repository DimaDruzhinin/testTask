package ru.cdek.task.dao;

import ru.cdek.task.entity.User;

import java.util.List;

/**
 * Класс служит слоем доступа к данным пользователя.
 */
public interface UserDao {

    /**
     * Создание нового пользователя в хранилище.
     *
     * @param user новый пользователь.
     * @return true, если пользователь создан.
     */
    boolean createUser(User user);

    /**
     * Получение всех пользователей из хранилища.
     *
     * @return список всех пользователей.
     */
    List<User> getAll();

    /**
     * Получение всех пользователей по диапазону идентификаторов.
     *
     * @param minId миниимальный идентификатор диапазона.
     * @param maxId максимальный идентификатор диапазона.
     * @return список пользователей.
     */
    List<User> findUsersByIdRange(long minId, long maxId);

    /**
     * Получение всех пользователей, идентификатор которых больше {@code minId}.
     *
     * @param minId миниимальный идентификатор диапазона.
     * @return список пользователей.
     */
    List<User> findUsersByIdMoreThan(long minId);

    /**
     * Получение всех пользователей, идентификатор которых меньше {@code maxId}.
     *
     * @param maxId максимальный идентификатор диапазона.
     * @return список пользователей.
     */
    List<User> findUsersByIdLessThan(long maxId);
}
