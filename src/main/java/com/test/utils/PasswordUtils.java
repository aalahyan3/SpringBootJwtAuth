package com.test.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hash(String raw)
    {
        return encoder.encode(raw);
    }
    public static boolean match(String raw, String hash)
    {
        return encoder.matches(raw, hash);
    }
}

