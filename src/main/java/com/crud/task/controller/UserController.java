package com.crud.task.controller;

import com.crud.task.command.CreateUserCommand;
import com.crud.task.command.UpdateUserCommand;
import com.crud.task.domain.User;
import com.crud.task.exception.UserNotFoundException;
import com.crud.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createNewUser(@RequestBody CreateUserCommand command) {
        User user = userService.createNewUser(command);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody UpdateUserCommand command) throws UserNotFoundException {
        User user = userService.updateUser(command);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId) throws UserNotFoundException {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(produces = "text/csv")
    public ResponseEntity<Resource> getUsers() {
        return userService.getUsers();
    }
}
