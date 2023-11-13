package com.crud.task.command;

import lombok.Data;

@Data
public class UpdateUserCommand {
    private Long id;
    private String name;
}
