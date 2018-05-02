package ru.cdek.task.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cdek.task.dao.UserDao;
import ru.cdek.task.dto.Response;
import ru.cdek.task.dto.UserFilter;
import ru.cdek.task.entity.User;
import ru.cdek.task.status.ResponseStatus;
import ru.cdek.task.validator.UserValidator;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    private UserValidator userValidator;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserDao userDao, UserValidator userValidator) {
        this.userDao = userDao;
        this.userValidator = userValidator;
    }

    @Override
    public Response save(User user) {
        if (!userValidator.validateUser(user)) {
            return new Response(ResponseStatus.INVALID_REQUEST);
        }
        LOGGER.debug("Near saving user with name {}", user.getName());
        try {
            userDao.createUser(user);
            LOGGER.debug("User with name {} created", user.getName());
            return new Response(ResponseStatus.SUCCESS);
        } catch (DataIntegrityViolationException e) {
            LOGGER.info("User with same name already exists", e);
            return new Response(ResponseStatus.USER_ALREADY_EXISTS);
        } catch (Exception e) {
            LOGGER.error("Some problem with database", e);
            return new Response(ResponseStatus.INTERNAL_ERROR);
        }
    }

    @Override
    public Response findByFilter(UserFilter filter) {
        if (!userValidator.validateFilter(filter)) {
            return new Response(ResponseStatus.INVALID_REQUEST);
        }
        try {
            List<User> userList;
            long minId = filter.getMinId();
            long maxId = filter.getMaxId();
            LOGGER.debug("Near getting all users by filter with id from {} to {}", minId, maxId);
                //открытый диапазон
            if (minId < 0 && maxId < 0) {
                userList = userDao.getAll();
                //ограничен сверху
            } else if (minId < 0 && maxId >= 0) {
                userList = userDao.findUsersByIdLessThan(maxId);
                //ограничен снизу
            } else if (minId >= 0 && maxId < 0) {
                userList = userDao.findUsersByIdMoreThan(minId);
                //ограничен сверху и снизу
            } else {
                userList = userDao.findUsersByIdRange(minId, maxId);
            }
            LOGGER.debug("Getting users by filter id. Count: {}", userList.size());
            Response response = new Response(ResponseStatus.SUCCESS);
            response.getUsers().addAll(userList);
            return response;
        } catch (Exception e) {
            LOGGER.error("Some problem with database", e);
            return new Response(ResponseStatus.INTERNAL_ERROR);
        }
    }
}
