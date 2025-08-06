/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author maxim
 */
@Entity
@Table(name = "\"user\"") //Экранировние потому что слово зарезервировано в SQL
@Data
public class User { //имплементируем на пользователя для определения springSecurity чтобы можно было брать экземпляр текущего пользователя из сессии
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate; //Дата без времени
    
    @Column(name = "is_delete")
    private boolean isDelete;
    
    @Column(name = "login")
    private String login;
    
    @Column(name = "password")
    private String password;
    
    @ManyToOne()
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projects = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Logs> logs = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Task> tasks = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Statistics> statistics = new ArrayList<>();
    
    // Методы для управления связью
    public void addStatistics(Statistics statistics) {
        this.statistics.add(statistics);
        statistics.setUser(this);
    }
    
    public void removeStatistics(Statistics statistics) {
        this.statistics.remove(statistics);
        statistics.setUser(null);
    }
}
