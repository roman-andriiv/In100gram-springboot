package com.andriiv.in100gram.services;

import com.andriiv.in100gram.dto.UserDTO;
import com.andriiv.in100gram.entities.User;
import com.andriiv.in100gram.entities.enums.Role;
import com.andriiv.in100gram.exceptions.UserExistException;
import com.andriiv.in100gram.payload.request.SignupRequest;
import com.andriiv.in100gram.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * Created by Roman Andriiv (30.04.2023 - 16:15)
 */
@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest userIn) {
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setFirstName(userIn.getFirstName());
        user.setLastName(userIn.getLastName());
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(Role.ROLE_USER);

        try {
            LOG.info("Saving User {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            LOG.error("Error during registration, {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() +
                    "already exist. Please check your credentials.");
        }
    }

    public User updateUser(UserDTO userDTO, Principal principal) {

        User user = getUserByPrincipal(principal);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setBio(userDTO.getBio());

        return userRepository.save(user);
    }

    public User getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
    }

    public User getUserById(Long userId) {
        return userRepository.findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID " + userId));

    }
}
