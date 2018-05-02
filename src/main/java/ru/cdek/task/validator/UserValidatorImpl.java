package ru.cdek.task.validator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.cdek.task.dto.UserFilter;
import ru.cdek.task.entity.User;

import java.util.Objects;

@Component
public class UserValidatorImpl implements UserValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserValidatorImpl.class);

    @Override
    public boolean validateUser(User user) {
        if (Objects.isNull(user)) {
            LOGGER.info("User is null");
            return false;
        }
        if (StringUtils.isEmpty(user.getName())) {
            LOGGER.info("Username is empty: ", user);
            return false;
        }
        return true;
    }

    @Override
    public boolean validateFilter(UserFilter filter) {
        if (Objects.isNull(filter)) {
            LOGGER.info("Filter is null");
            return false;
        }
        //если число отрицательное, то считаем эту границу не заданной, но валидной.
        if (filter.getMinId() >= 0 && filter.getMaxId() >= 0) {
            if (filter.getMinId() > filter.getMaxId()) {
                LOGGER.info("MaxId must be more than minId");
                return false;
            }
        }
        return true;
    }
}
