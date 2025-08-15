/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.dto;

import com.mycompany.taskmanager.entity.Task;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author User
 */
@Data
public class TaskDto {
    private Long id;
    private String code;
    private String name;
    private String description;
    private LocalDate dateBegin;
    private LocalDate dateEnd;
    private LocalDateTime time;
    private UserDto user;
    private ProjectDto project;
    private StatusDto status;
    private StatisticsDto statistics;
    
    public TaskDto(Task task) {
        this.id = task.getId();
        this.code = task.getCode();
        this.name = task.getName();
        this.description = task.getDescription();
        this.dateBegin = task.getDateBegin();
        this.dateEnd = task.getDateEnd();
        this.time = task.getTime();
        this.user = new UserDto(task.getUser());
        this.project = new ProjectDto(task.getProject());
        this.status = new StatusDto(task.getStatus());
        this.statistics = new StatisticsDto(task.getStatistics());
    }
}
