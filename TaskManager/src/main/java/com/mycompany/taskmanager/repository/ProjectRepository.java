/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.taskmanager.repository;

import com.mycompany.taskmanager.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author User
 */
public interface ProjectRepository extends JpaRepository <Project, Long> {
    // Пагинация работает "из коробки" - просто добавляем Pageable
    //@Override
    @Query("""
           SELECT p FROM Project p
           WHERE (:name IS NULL OR p.name LIKE %:name%)
           """)
    Page<Project> findAllByName(Pageable pageable, @Param("name") String name);
}
