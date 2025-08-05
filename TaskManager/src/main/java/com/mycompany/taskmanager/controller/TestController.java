/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author maxim
 */
@RestController
public class TestController {

    // Доступен всем
    @GetMapping("/api/public/hello")
    public String publicHello() {
        return "Public hello! No auth needed!";
    }

    // Только для аутентифицированных
    @GetMapping("/api/user/me")
    public String userEndpoint() {
        return "User area! Your login: " + 
            SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // Только для ADMIN
    @GetMapping("/api/admin/data")
    public String adminEndpoint() {
        return "Super secret admin data!";
    }
    
    @GetMapping("/api/debug/roles")
    public String debugRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "Текущие роли: " + auth.getAuthorities().toString();
    }
}
