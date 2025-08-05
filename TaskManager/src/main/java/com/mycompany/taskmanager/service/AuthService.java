/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.service;

import com.mycompany.taskmanager.entity.User;
import com.mycompany.taskmanager.repository.UserRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Это сервис, который проверяет логин/пароль. 
 * (Здесь спринг сам проверяет пароли, точнее он это делает в самой конфигурации SecurityConfig)
 * 
 * @author maxim
 */
@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository; //для взятия пользователя по логину

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
//        admin -> "$2a$10$MUQ9R6jVrBNTg8J4HkGHBu/i5Jdjo0llzB8Qu9D0OqE9rTUbDDN/2"

        // 1. Ищем пользователя в БД
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        // 2. Проверяем, не заблокирован ли он
        if (user.isDelete()) {
            throw new UsernameNotFoundException("Пользователь заблокирован");
        }

        // 3. Возвращаем пользователя для Spring Security
        return new org.springframework.security.core.userdetails.User(
            user.getLogin(),
            user.getPassword(), // Хэш из БД (например: "$2a$10$N9qo8uLO...")
            Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().getName()) // Роль из БД
            )
        );
    }
}
