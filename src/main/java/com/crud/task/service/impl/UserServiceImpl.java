package com.crud.task.service.impl;

import com.crud.task.command.CreateUserCommand;
import com.crud.task.command.UpdateUserCommand;
import com.crud.task.domain.User;
import com.crud.task.exception.UserNotFoundException;
import com.crud.task.repository.UserRepository;
import com.crud.task.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void deleteUser(Long userId) throws UserNotFoundException {
        log.info("Delete user by id: {}", userId);
        User user = findUserById(userId);
        userRepository.delete(user);
    }

    @Override
    public User getUserById(Long userId) throws UserNotFoundException {
        log.info("Get user by id: {}", userId);
        return findUserById(userId);
    }

    @Override
    public User createNewUser(CreateUserCommand command) {
        log.info("Create new user with name: {}", command.getName());
        User user = User.builder().username(command.getName()).build();
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UpdateUserCommand command) throws UserNotFoundException {
        User user = findUserById(command.getId());
        user.setUsername(command.getName());
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<Resource> getUsers() {
        String[] csvHeaders = {"id", "username"};

        List<User> users = userRepository.findAll();

        ByteArrayInputStream byteArrayOutputStream = generateCsv(users, csvHeaders);

        InputStreamResource fileInputStream = new InputStreamResource(byteArrayOutputStream);
        String csvFileName = "users.csv";

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

        return new ResponseEntity<>(fileInputStream, headers, HttpStatus.OK);
    }

    private User findUserById(Long userId) throws UserNotFoundException {
        return userRepository.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private ByteArrayInputStream generateCsv(List<User> users, String[] csvHeaders) {
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                CSVPrinter csvPrinter = new CSVPrinter(
                        new PrintWriter(out),
                        CSVFormat.DEFAULT.withHeader(csvHeaders)
                )
        ) {
            users.stream()
                    .map(user -> Arrays.asList(user.getId().toString(), user.getUsername()))
                    .forEach(record -> {
                        try {
                            csvPrinter.printRecord(record);
                        } catch (IOException e) {
                            throw new RuntimeException("Error printing CSV record", e);
                        }
                    });

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Error generating CSV", e);
        }
    }
}
