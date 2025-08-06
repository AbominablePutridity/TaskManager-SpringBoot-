/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.dto;

import com.mycompany.taskmanager.entity.Role;
import com.mycompany.taskmanager.entity.User;
import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author maxim
 */
@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private boolean isDelete;
    private String login;
    private RoleDto role;
    
    public UserDto(User user) {
        id = user.getId();
        firstName = user.getFirstName() != null ? user.getFirstName() : "";
        lastName = user.getLastName() != null ? user.getLastName() : "";
        birthDate = user.getBirthDate(); // LocalDate может быть null
        isDelete = user.isDelete();
        login = user.getLogin() != null ? user.getLogin() : "";
        role = user.getRole() != null ? new RoleDto(user.getRole()) : null;
    }
}
