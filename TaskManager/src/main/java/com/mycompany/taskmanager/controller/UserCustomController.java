/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.dto.UserDto;
import com.mycompany.taskmanager.entity.User;
import com.mycompany.taskmanager.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author maxim
 */
@RestController
//@RequestMapping("/api")  // Базовый путь
public class UserCustomController {
    private final UserRepository userRepository;


    public UserCustomController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //!!! ГОСТЬ
    
    // POST → /api/public/user
    @PostMapping("/api/public/user")
    public ResponseEntity<?> createUserPublic(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(new UserDto(savedUser));
    }

    //!!! ПОЛЬЗОВАТЕЛЬ
    
    // GET (коллекция) → /api/user/user
    @GetMapping("/api/user/user")
    public ResponseEntity<?> getAllUsers() {
        List<UserDto> usersDto = new ArrayList<>();
        
        for(User user : userRepository.findAll()) {
            usersDto.add(new UserDto(user));
        }
        
        return ResponseEntity.ok(usersDto);
    }
    
    // GET (одна запись) → /api/user/user/{id}
    @GetMapping("/api/user/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        List<UserDto> usersDto = new ArrayList<>();
        
        for(User user : userRepository.findById(id).stream().toList()) {
            usersDto.add(new UserDto(user));
        }
        
        return ResponseEntity.ok(usersDto);
    }

    // PUT/PATCH → /api/user/user/{id}
    @PutMapping("/api/user/editMe")
    public ResponseEntity<?> updateUser(@RequestBody User user, Authentication authentication) {
        User currentUser = userRepository.findByLogin(authentication.getName());
        
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setBirthDate(user.getBirthDate());
        
        return ResponseEntity.ok(new UserDto(userRepository.save(currentUser)));
    }
    
    //!!! АДМИН
    
    // PUT/PATCH → /api/admin/user/{id}
    @PutMapping("/api/admin/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(new UserDto(updatedUser));
    }

    // DELETE → /api/admin/user/{id}
    @DeleteMapping("/api/admin/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
