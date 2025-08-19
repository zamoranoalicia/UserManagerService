package org.azamorano.usermanagerservice.service.user;

import org.azamorano.usermanagerservice.entity.User;
import org.azamorano.usermanagerservice.persistence.user.UserRepository;
import org.azamorano.usermanagerservice.rest.controller.user.dto.UserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class UserService {
    private static final String USER_ALREADY_EXIST = "User %s already registered";
    private static final String ERROR_WHILE_CREATING_USER = "There was an error while creating %s user. Please " +
            "contact IT Services";
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        try {
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

            if (existingUser.isPresent()) {
                throw new UserCreationException(String.format(USER_ALREADY_EXIST, user.getEmail()));
            }

            return userRepository.save(user);
        } catch (Exception exception) {
            throw new UserCreationException(String.format(ERROR_WHILE_CREATING_USER, user.getEmail()));
        }
    }
}
