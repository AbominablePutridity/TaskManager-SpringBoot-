/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.dto;

import com.mycompany.taskmanager.entity.Project;
import lombok.Data;

/**
 *
 * @author User
 */
@Data
public class ProjectDto {
    private Long id;
    private String name;
    private boolean isDelete;
    
    public ProjectDto(Project project) {
        id = project.getId();
        this.name = project.getName() != null ? project.getName() : "";
        this.isDelete = project.isDelete();
    }
}
