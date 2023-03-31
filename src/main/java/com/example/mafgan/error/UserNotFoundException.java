package com.example.mafgan.error;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String user_not_available) {
        super(user_not_available);
    }
}
