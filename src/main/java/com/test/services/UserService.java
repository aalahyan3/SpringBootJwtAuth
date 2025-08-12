package com.test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.exceptions.InvalidEmailException;
import com.test.exceptions.UsernameAlreadyExistsException;
import com.test.models.User;
import com.test.repositories.UserRepository;
import com.test.utils.PasswordUtils;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUser(User user)
    {
        if (userRepository.findByUsername(user.getUsername()) != null)
            throw new UsernameAlreadyExistsException("username '" + user.getUsername() + "' is already taken");
        if (!user.getEmail().matches("[^@]+@[^.]+\\..+"))
            throw new InvalidEmailException("email '" + user.getEmail() + "'' is invalid");
        user.setPassword(PasswordUtils.hash(user.getPassword()));
        return userRepository.save(user);
    }

    public User searchUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
