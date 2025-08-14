/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.dto.TaskDto;
import com.mycompany.taskmanager.entity.Project;
import com.mycompany.taskmanager.entity.Task;
import com.mycompany.taskmanager.entity.User;
import com.mycompany.taskmanager.repository.TaskRepository;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
public class TaskController {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    
    public TaskController(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }
    
    //!!! ПОЛЬЗОВАТЕЛЬ
    
    // GET (коллекция) → api/user/myTasks (видит все свои таски)
    @GetMapping("api/user/myTasks")
    public List<TaskDto> getMyTasks(Authentication authentication) {
        User currentUser = userRepository.findByLogin(authentication.getName());
        
        List<Task> tasks = new ArrayList<>();
        for (Project project : currentUser.getProjects()) {
            if (project.isDelete() == false) {
                tasks = project.getTasks();
            }
        }
        
        List<TaskDto> tasksResult = new ArrayList<>();
        for (Task task : tasks) {
            tasksResult.add(new TaskDto(task));
        }
        
        return tasksResult;
    }
    
    //!!! АДМИН
    
    /*
    Для Pageble:
    Параметры запроса по умолчанию:

    page – номер страницы (начинается с 0)

    size – количество элементов на странице

    sort – сортировка (необязательно)
     */
    @GetMapping("api/admin/getAllTasks")
    public Page<TaskDto> getAllProjects(Pageable pageable){
        return taskRepository.findAll(pageable).map(task -> new TaskDto(task));
    }
    
    @PostMapping("/api/admin/task")
    public TaskDto createProject(@RequestBody Task task) {
        return new TaskDto(taskRepository.save(task));
    }
    
    // PUT/PATCH → /api/admin/user/{id}
    @PutMapping("/api/admin/task/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody Task task) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        task.setId(id);
        Task updatedTask = taskRepository.save(task);
        return ResponseEntity.ok(new TaskDto(updatedTask));
    }
    
    // DELETE → /api/admin/user/{id}
    @DeleteMapping("/api/admin/task/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
