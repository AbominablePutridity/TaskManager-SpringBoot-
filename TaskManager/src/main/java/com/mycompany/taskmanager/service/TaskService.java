/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.service;

import com.mycompany.taskmanager.dto.TaskDto;
import com.mycompany.taskmanager.entity.Project;
import com.mycompany.taskmanager.entity.Task;
import com.mycompany.taskmanager.entity.User;
import com.mycompany.taskmanager.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author User
 */
@Service
public class TaskService {
    private final UserRepository userRepository;
    
    public TaskService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public List<TaskDto> getUserTasks(@PathVariable Long id, Authentication authentication) {
        User currentUser = userRepository.findByLogin(authentication.getName());
        
        List<Task> tasks = new ArrayList<>();
        for (Project project : currentUser.getProjects()) {
            if (project.isDelete() == false) {
                tasks = project.getTasks();
            }
        }
        
        List<TaskDto> tasksResult = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getProject().getId() == id) {
                tasksResult.add(new TaskDto(task));
            }
        }
        
        return tasksResult;
    }
}
