/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.dto.StatisticsDto;
import com.mycompany.taskmanager.dto.TaskDto;
import com.mycompany.taskmanager.entity.Statistics;
import com.mycompany.taskmanager.entity.Task;
import com.mycompany.taskmanager.entity.User;
import com.mycompany.taskmanager.repository.StatisticsRepository;
import com.mycompany.taskmanager.repository.TaskRepository;
import com.mycompany.taskmanager.repository.UserRepository;
import com.mycompany.taskmanager.service.TaskService;
import java.util.ArrayList;
import java.util.List;
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
public class StatisticsController {
    private final UserRepository userRepository;
    private final StatisticsRepository statisticsRepository;
    private final TaskService taskService;
    private final TaskRepository taskRepository;
    
    public StatisticsController(
        UserRepository userRepository,
        StatisticsRepository statisticsRepository,
        TaskService taskService,
        TaskRepository taskRepository
    ) {
        this.userRepository = userRepository;
        this.statisticsRepository = statisticsRepository;
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    // !!! Пользователь
    @GetMapping("api/user/task/{id}/myStatistics")
    public List<StatisticsDto> getAllProjects(@PathVariable Long id, Pageable pageable, Authentication authentication){
        List<TaskDto> tasksCurrentUser = taskService.getUserTasks(id, authentication);
        
        List<StatisticsDto> statisticsResult = new ArrayList<>();
        for (TaskDto taskDto : tasksCurrentUser) {
            if (taskDto.getId() == id) {
                statisticsResult.add(taskDto.getStatistics());
            }
        }
        
        return statisticsResult;
    }
    
    @PostMapping("/api/user/setStatistics")
    public StatisticsDto createProject(@RequestBody Statistics statistics, Authentication authentication) {
        User currentUser = userRepository.findByLogin(authentication.getName());
        
        statistics.setUser(currentUser);
        
        return new StatisticsDto(statisticsRepository.save(statistics));
    }
    
    // PUT/PATCH → /api/admin/user/{id}
    @PutMapping("/api/user/statistics/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody Statistics statistics, Authentication authentication) {
        User currentUser = userRepository.findByLogin(authentication.getName());
        
        if (!statisticsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        if(statistics.getUser().getId() == currentUser.getId()) {
            statistics.setId(id);
            Statistics updatedStatistics = statisticsRepository.save(statistics);
            return ResponseEntity.ok(new StatisticsDto(updatedStatistics));
        } else {
            return ResponseEntity.ok("Ошибка: вы пытаетесь редактировать не свою запись!");
        }
    }
    
    // DELETE → /api/admin/user/{id}
    @DeleteMapping("/api/user/statistics/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        
        
        if (!statisticsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        statisticsRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    
    // !!! Админ
    @GetMapping("api/admin/task/{id}/getAllStatistics")
    public List<StatisticsDto> getAllProjects(@PathVariable Long id, Pageable pageable){
        //return statisticsRepository.findAll(pageable).map(statistics -> new StatisticsDto(statistics));
        
        List<StatisticsDto> statisticsResult = new ArrayList<>();
        for (Task task : taskRepository.findById(id).stream().toList()) {
            if (task.getId() == id) {
                statisticsResult.add(new StatisticsDto(task.getStatistics()));
            }
        }
        
        return statisticsResult;
    }
}
