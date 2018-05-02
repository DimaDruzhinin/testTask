package ru.cdek.task.validator;

import ru.cdek.task.dto.UserFilter;
import ru.cdek.task.entity.User;

/**
 * Реализации служат для валидации пользователя по его полям.
 * todo: Выпилить самописный валидатор и научить спринг валидировать по JSON-схеме.
 */
public interface UserValidator {

    /**
     * Валидирует пользователя по его полям.
     *
     * @param user пользователь, которого нужно провалидировать.
     * @return true, если валидация прошла успешно.
     */
    boolean validateUser(User user);

    /**
     * Валидирует фильтр запроса пользователей.
     *
     * @param filter фильтр запроса пользователей.
     * @return true, если валидация прошла успешно.
     */
    boolean validateFilter(UserFilter filter);
}
