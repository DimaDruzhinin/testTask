package ru.cdek.task.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.cdek.task.dto.Response;
import ru.cdek.task.dto.UserFilter;
import ru.cdek.task.entity.User;
import ru.cdek.task.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Response createUser(@RequestBody User newUser) {
        return userService.save(newUser);
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Response getByFilter(@RequestBody UserFilter filter) {
        System.out.println("!!!!!!!!!!");
        return userService.findByFilter(filter);
    }

}
