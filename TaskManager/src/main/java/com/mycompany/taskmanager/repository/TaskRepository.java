/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.taskmanager.repository;

import com.mycompany.taskmanager.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author User
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Пагинация работает "из коробки" - просто добавляем Pageable
    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId")
    Page<Task> findAll(Pageable pageable, Long projectId);
}
