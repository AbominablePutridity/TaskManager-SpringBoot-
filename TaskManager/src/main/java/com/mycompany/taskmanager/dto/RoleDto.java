/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.dto;

import com.mycompany.taskmanager.entity.Role;
import lombok.Data;

/**
 *
 * @author maxim
 */
@Data
public class RoleDto {
    private Long id;
    private String name;
    
    public RoleDto(Role role) {
        id = role.getId();
        name = role.getName() != null ? role.getName() : "";
    }
}
