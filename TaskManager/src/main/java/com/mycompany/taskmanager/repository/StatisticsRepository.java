/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.taskmanager.repository;

import com.mycompany.taskmanager.entity.Statistics;
import com.mycompany.taskmanager.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    // Пагинация работает "из коробки" - просто добавляем Pageable
    @Override
    Page<Statistics> findAll(Pageable pageable);
}
