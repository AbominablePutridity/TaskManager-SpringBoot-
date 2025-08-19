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
import org.springframework.web.bind.annotation.RequestParam;
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
    
    
    /*
    Для Pageble:
    Параметры запроса по умолчанию:

    page – номер страницы (начинается с 0)

    size – количество элементов на странице

    sort – сортировка (необязательно)
     */
    // GET (коллекция) → /api/user/user
    @GetMapping("/api/user/user")
    public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        // Получаем страницу пользователей из репозитория
        Page<User> usersPage = userRepository.findAllByName(pageable, firstName, lastName);

        // Преобразуем Page<User> в Page<UserDto>
        Page<UserDto> usersDtoPage = usersPage.map(user -> new UserDto(user));

        return ResponseEntity.ok(usersDtoPage);
    }
    
    // GET (текущий пользователь) → /api/user/user
    @GetMapping("/api/user/me")
    public UserDto getCurrentUser(Authentication authentication) {
        User currentUser = userRepository.findByLogin(authentication.getName());
        
        return new UserDto(currentUser);
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
