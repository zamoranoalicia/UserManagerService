package org.azamorano.usermanagerservice.service.user;

import org.azamorano.usermanagerservice.entity.User;
import org.azamorano.usermanagerservice.persistence.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
public class UserService implements UserDetailsService {
    private static final String USER_ALREADY_EXIST = "User %s already registered";
    private static final String ERROR_WHILE_CREATING_USER = "There was an error while creating %s user. Please " +
            "contact IT Services";
    private static final String USER_NOT_FOUND = "User not found: %s";
    private static final String USER_ROLE = "USER";
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User registerUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new UserCreationException(
                    String.format(USER_ALREADY_EXIST, user.getEmail()), CONFLICT);
        }

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, username)));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(USER_ROLE)
                .build();
    }

    public User searchUserByUserName(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, username)));
    }


}
