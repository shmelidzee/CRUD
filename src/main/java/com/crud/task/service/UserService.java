package com.crud.task.service;

import com.crud.task.command.CreateUserCommand;
import com.crud.task.command.UpdateUserCommand;
import com.crud.task.domain.User;
import com.crud.task.exception.UserNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface UserService {

    void deleteUser(Long userId) throws UserNotFoundException;

    User getUserById(Long userId) throws UserNotFoundException;

    User createNewUser(CreateUserCommand command);

    User updateUser(UpdateUserCommand command) throws UserNotFoundException;

    ResponseEntity<Resource> getUsers();

}
