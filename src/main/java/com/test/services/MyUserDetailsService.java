package com.test.services;

import java.util.ArrayList;

// import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.models.User;
import com.test.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepo)
    {
        this.userRepository = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("user was not found");
        
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
        .password(user.getPassword()).authorities(new ArrayList<>()).accountLocked(false).accountExpired(false).disabled(false).build();
    }
}

