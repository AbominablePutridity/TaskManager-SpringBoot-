/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager;

import com.mycompany.taskmanager.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Главная конфигурация Spring Security
 * 
 * @author maxim
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthService authService; // Подключаем наш AuthService
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Говорим Spring, что пароли хешируются BCrypt
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.addAllowedOrigin("*"); // ← Разрешить ВСЕ домены
                config.addAllowedMethod("*"); // ← Разрешить ВСЕ методы
                config.addAllowedHeader("*"); // ← Разрешить ВСЕ заголовки
                return config;
            })) // Настраиваем CORS на любой домен, любой метод и любые заголовки (для локального фронта/jQuery запросов браузеру на бэк)
                
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").hasAuthority("ADMIN") // Проверка ролей
                .requestMatchers("/api/user/**").hasAnyAuthority("USER", "ADMIN") //если роль админ, то он ноже может делать те же вещи что и пользователь, только еще и свои
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults()) // Включаем Basic Auth
            .userDetailsService(authService) // Указываем, какой сервис использовать для авторизации
            
            .logout(logout -> logout
                .logoutUrl("/api/auth/logout").permitAll() // URL для выхода
                .logoutSuccessUrl("/api/public/hello") // Перенаправление после выхода
                .invalidateHttpSession(true) // Уничтожить сессию
                .deleteCookies("JSESSIONID")); // Удалить куки
                
        
        return http.build();
    }
}
