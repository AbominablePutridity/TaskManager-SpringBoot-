/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.dto;

import com.mycompany.taskmanager.entity.Statistics;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author User
 */
@Data
public class StatisticsDto {
    private Long id;
    private String description;
    private LocalDateTime time;
    
    public StatisticsDto(Statistics statistics){
        this.id = statistics.getId();
        this.time = statistics.getTime();
        this.description = statistics.getDescription();
    }
}
