package com.example.schoolstuff.models;

import java.util.Optional;

public class UserManager {
    public static Optional<User> savedUser = Optional.empty();

    public static void setSavedUser(User user) {
        savedUser = Optional.of(user);
    }

    public static Optional<User> getSavedUser() {
        return savedUser;
    }
}
