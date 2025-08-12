/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.dto.ProjectDto;
import com.mycompany.taskmanager.entity.Project;
import com.mycompany.taskmanager.entity.User;
import com.mycompany.taskmanager.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
public class ProjectController {
    private final UserRepository userRepository;
    
    public ProjectController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    //!!! ПОЛЬЗОВАТЕЛЬ
    
    // GET (коллекция) → /api/user/user
    @GetMapping("api/user/myProjects")
    public List<ProjectDto> getMyProjects(Authentication authentication) {
        User currentUser = userRepository.findByLogin(authentication.getName());
        
        List<ProjectDto> result = new ArrayList<>();
        for (Project project : currentUser.getProjects()) {
            if (project.isDelete() == false) {
                result.add(new ProjectDto(project));
            }
        }
        
        return result;
    }
    
}
