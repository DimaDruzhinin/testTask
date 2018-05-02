package ru.cdek.task.service;

import ru.cdek.task.dto.Response;
import ru.cdek.task.dto.UserFilter;
import ru.cdek.task.entity.User;

/**
 * Сервис по работе с пользователями. В этом слое происходит обращение в ДАО,
 * логгирование, валидация, формирование ответов.
 */
public interface UserService {

    /**
     * Сохранение пользователя в хранилище.
     *
     * @param user пользователь, которого следует сохранить.
     * @return сформированный ответ со статусом выполнения операции.
     */
    Response save(User user);

    /**
     * Получение всех пользователей по фильтру.
     *
     * @param filter фильтр пользователей.
     * @return Сформированный ответ со статусом выполнения операции.
     */
    Response findByFilter(UserFilter filter);
}
