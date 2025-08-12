/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.taskmanager.repository;

import com.mycompany.taskmanager.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface ProjectRepository extends JpaRepository <Project, Long> {

}
