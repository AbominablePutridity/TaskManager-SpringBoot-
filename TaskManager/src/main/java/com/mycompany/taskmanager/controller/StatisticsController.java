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

/**
 *
 * @author User
 */
public class StatisticsController {
    private final UserRepository userRepository;
    private final StatisticsRepository statisticsRepository;
    
    public StatisticsController(UserRepository userRepository, StatisticsRepository statisticsRepository) {
        this.userRepository = userRepository;
        this.statisticsRepository = statisticsRepository;
    }

    // !!! Пользователь
    @GetMapping("api/user/myStatistics")
    public List<Statistics> getAllProjects(Pageable pageable, Authentication authentication){
        User currentUser = userRepository.findByLogin(authentication.getName());
        
        return currentUser.getStatistics();//statisticsRepository.findAll(pageable).map(task -> new StatisticsDto(task));
    }
    
    @PostMapping("/api/user/setStatistics")
    public StatisticsDto createProject(@RequestBody Statistics statistics, Authentication authentication) {
        User currentUser = userRepository.findByLogin(authentication.getName());
        
        statistics.setUser(currentUser);
        
        return new StatisticsDto(statisticsRepository.save(statistics));
    }
    
    // PUT/PATCH → /api/admin/user/{id}
    @PutMapping("/api/user/statistics/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody Statistics statistics) {
        if (!statisticsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        statistics.setId(id);
        Statistics updatedStatistics = statisticsRepository.save(statistics);
        return ResponseEntity.ok(new StatisticsDto(updatedStatistics));
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
    @GetMapping("api/admin/getAllStatistics")
    public Page<StatisticsDto> getAllProjects(Pageable pageable){
        return statisticsRepository.findAll(pageable).map(statistics -> new StatisticsDto(statistics));
    }
}
