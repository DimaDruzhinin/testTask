package ru.cdek.task.validator;

import org.testng.annotations.Test;
import ru.cdek.task.entity.User;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class UserValidatorImplTest {

    private UserValidator userValidator = new UserValidatorImpl();

    @Test
    public void validateUserTest() {
        assertFalse(userValidator.validateUser(null));
        User fakeUser = new User(null);
        assertFalse(userValidator.validateUser(fakeUser));
        fakeUser.setName("");
        assertFalse(userValidator.validateUser(fakeUser));
        fakeUser.setName("John");
        assertTrue(userValidator.validateUser(fakeUser));
    }

    @Test
    public void validateFilterTest() {
        //todo:
    }
}
