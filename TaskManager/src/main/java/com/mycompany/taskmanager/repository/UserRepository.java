/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.taskmanager.repository;

import com.mycompany.taskmanager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author maxim
 */
@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
    
    //@Override // переопределяем, тк интерфейс JpaRepository по умолчанию имеет этот метод
    @Query("""
           SELECT u FROM User u 
           WHERE (:firstName IS NULL OR u.firstName LIKE %:firstName%)
           AND (:lastName IS NULL OR u.lastName LIKE %:lastName%)
           """)
    public Page<User> findAllByName(Pageable pageable, @Param("firstName") String firstName, @Param("lastName") String lastName); //pageable - для пагинации
}
