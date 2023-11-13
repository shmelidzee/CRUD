package com.crud.task.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(Long id) {
        super("User not found, id: " + id);
    }
}
