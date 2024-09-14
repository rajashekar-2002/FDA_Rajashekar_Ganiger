package com.EventManagement.eventManagement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EventManagement.eventManagement.model.MyUser;



public interface MyUserRepository extends JpaRepository<MyUser,Long>{
    Optional<MyUser> findByUsername(String username);

}
