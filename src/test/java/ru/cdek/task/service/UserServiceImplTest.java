package ru.cdek.task.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.cdek.task.dao.UserDao;
import ru.cdek.task.dto.Response;
import ru.cdek.task.dto.UserFilter;
import ru.cdek.task.entity.User;
import ru.cdek.task.status.ResponseStatus;
import ru.cdek.task.validator.UserValidator;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @Mock
    private UserValidator userValidator;

    @BeforeTest
    public void init() {
        initMocks(this);
    }

    @Test
    public void saveSuccess() {
        //GIVEN
        User fakeUser = getFakeUser();
        when(userValidator.validateUser(fakeUser)).thenReturn(true);
        when(userDao.createUser(fakeUser)).thenReturn(true);

        //WHEN
        Response response = userService.save(fakeUser);

        //THEN
        verify(userValidator, times(1)).validateUser(fakeUser);
        verify(userDao, times(1)).createUser(fakeUser);
        assertEquals(response.getStatus(), ResponseStatus.SUCCESS);
    }

    @Test
    public void saveInvalidUser() {
        //GIVEN
        User fakeUser = getFakeUser();
        when(userValidator.validateUser(fakeUser)).thenReturn(false);

        //WHEN
        Response response = userService.save(fakeUser);

        //THEN
        assertEquals(response.getStatus(), ResponseStatus.INVALID_REQUEST);
    }

    @Test
    public void saveExistedUser() {
        //GIVEN
        User fakeUser = getFakeUser();
        when(userValidator.validateUser(fakeUser)).thenReturn(true);
        when(userDao.createUser(fakeUser)).thenThrow(new DataIntegrityViolationException("test"));

        //WHEN
        Response response = userService.save(fakeUser);

        //THEN
        assertEquals(response.getStatus(), ResponseStatus.USER_ALREADY_EXISTS);
    }

    @Test
    public void saveWithError() {
        //GIVEN
        User fakeUser = getFakeUser();
        when(userValidator.validateUser(fakeUser)).thenReturn(true);
        when(userDao.createUser(fakeUser)).thenThrow(new RuntimeException("test"));

        //WHEN
        Response response = userService.save(fakeUser);

        //THEN
        assertEquals(response.getStatus(), ResponseStatus.INTERNAL_ERROR);
    }

    @Test
    public void findBuFilterSuccess() {
        //GIVEN
        UserFilter filter = new UserFilter(1, 2);
        when(userValidator.validateFilter(filter)).thenReturn(true);
        //заданы обе границы фильтра
        Response response1 = userService.findByFilter(filter);
        verify(userDao, times(1)).findUsersByIdRange(filter.getMinId(), filter.getMaxId());
        assertEquals(response1.getStatus(), ResponseStatus.SUCCESS);
        //задана верхняя
        filter.setMinId(-1);
        Response response2 = userService.findByFilter(filter);
        verify(userDao, times(1)).findUsersByIdLessThan(filter.getMaxId());
        assertEquals(response2.getStatus(), ResponseStatus.SUCCESS);
        //задана нижняя
        filter.setMaxId(-1);
        filter.setMinId(2);
        Response response3 = userService.findByFilter(filter);
        verify(userDao, times(1)).findUsersByIdMoreThan(filter.getMinId());
        assertEquals(response3.getStatus(), ResponseStatus.SUCCESS);
        //фильтр не задан
        filter.setMinId(-1);
        filter.setMaxId(-1);
        Response response4 = userService.findByFilter(filter);
        verify(userDao, times(1)).getAll();
        assertEquals(response4.getStatus(), ResponseStatus.SUCCESS);
    }

    @Test
    public void findByFilterInvalid() {
        //GIVEN
        UserFilter filter = new UserFilter(2, 1);
        when(userValidator.validateFilter(filter)).thenReturn(false);

        //WHEN
        Response response = userService.findByFilter(filter);

        //THEN
        assertEquals(response.getStatus(), ResponseStatus.INVALID_REQUEST);
    }

    @Test
    public void findByFilterError() {
        //GIVEN
        UserFilter filter = new UserFilter(-1, -1);
        when(userValidator.validateFilter(filter)).thenReturn(true);
        when(userDao.getAll()).thenThrow(new RuntimeException("test"));

        //WHEN
        Response response = userService.findByFilter(filter);

        //THEN
        assertEquals(response.getStatus(), ResponseStatus.INTERNAL_ERROR);
    }

    private User getFakeUser() {
        return new User("John", 1l);
    }
}
