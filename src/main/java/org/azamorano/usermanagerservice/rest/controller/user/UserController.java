package org.azamorano.usermanagerservice.rest.controller.user;

import org.azamorano.usermanagerservice.service.user.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/users")
public class UserController {
    private UserService userService;
}
