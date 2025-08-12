package com.test.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.dto.LoginRequest;
import com.test.exceptions.InvalidCredsException;
import com.test.models.User;
import com.test.payload.ApiResponse;
import com.test.services.MyUserDetailsService;
import com.test.services.UserService;
import com.test.utils.JwtTokenUtil;
import com.test.utils.PasswordUtils;

import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final JwtTokenUtil jwtTokenUtil;
    private final MyUserDetailsService userDetailsService;

    public UserController(JwtTokenUtil jwtTokenUtil, MyUserDetailsService muds)
    {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = muds;
    }

   @Autowired
    private UserService userService; 

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@Valid @RequestBody User user)
    {
        User savedUser = userService.addUser(user);
        ApiResponse<User> res = new ApiResponse<User>(200, true, "user registred successfully", savedUser);
        String token = jwtTokenUtil.genrateToken(savedUser.getUsername());

        return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        .body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@Valid @RequestBody LoginRequest creds)
    {
        User user = userService.searchUserByUsername(creds.getUsername());
        if (user == null)
            throw new InvalidCredsException("invalid username or password");
        if (!PasswordUtils.match(creds.getPassword(), user.getPassword()))
            throw new InvalidCredsException("invalid username or password");
        ApiResponse<Object> res = new ApiResponse<Object>(200, true, "user Authentcated successfully", null);
        String token = jwtTokenUtil.genrateToken(user.getUsername());
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).body(res);
    }

    @GetMapping("/whoami")
    public ResponseEntity<ApiResponse<Object>> whoami(@RequestHeader(value = "Authorization", required = false) String token)
    {
        String username = "unknown";
        if (token != null && token.startsWith("Bearer "))
        {
            try
            {
                username = jwtTokenUtil.getUsernamefromToken(token.substring(7));
            }catch(Exception e)
            {
                username = "unknown";
            }
        }
        ApiResponse<Object> res = new ApiResponse<Object>(200, true, "you are " + username, null);
        return ResponseEntity.ok(res);
    }
}
