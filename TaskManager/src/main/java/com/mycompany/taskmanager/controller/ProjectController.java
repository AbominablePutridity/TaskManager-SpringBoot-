/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.dto.ProjectDto;
import com.mycompany.taskmanager.dto.UserDto;
import com.mycompany.taskmanager.entity.Project;
import com.mycompany.taskmanager.entity.User;
import com.mycompany.taskmanager.repository.ProjectRepository;
import com.mycompany.taskmanager.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
public class ProjectController {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    
    public ProjectController(UserRepository userRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
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
    
    //!!! АДМИН
    
    /*
    Для Pageble:
    Параметры запроса по умолчанию:

    page – номер страницы (начинается с 0)

    size – количество элементов на странице

    sort – сортировка (необязательно)
     */
    @GetMapping("api/admin/getAllProjects")
    public Page<ProjectDto> getAllProjects(Pageable pageable, @RequestParam(required = false) String name){
        return projectRepository.findAllByName(pageable, name).map(project -> new ProjectDto(project));
    }
    
    @PostMapping("/api/admin/project")
    public ProjectDto createProject(@RequestBody Project project) {
        return new ProjectDto(projectRepository.save(project));
    }
    
    // PUT/PATCH → /api/admin/user/{id}
    @PutMapping("/api/admin/project/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody Project project) {
        if (!projectRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        project.setId(id);
        Project updatedProject = projectRepository.save(project);
        return ResponseEntity.ok(new ProjectDto(updatedProject));
    }
    
    // DELETE → /api/admin/user/{id}
    @DeleteMapping("/api/admin/project/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!projectRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        projectRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
