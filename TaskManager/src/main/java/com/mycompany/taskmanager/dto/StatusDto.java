/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.dto;

import com.mycompany.taskmanager.entity.Status;
import lombok.Data;

/**
 *
 * @author User
 */
@Data
public class StatusDto {
    private Long id;
    private String name;
    
    public StatusDto(Status status){
        this.id = status.getId();
        this.name = status.getName();
    }
}
