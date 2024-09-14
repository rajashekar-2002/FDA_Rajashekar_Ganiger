package com.EventManagement.eventManagement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EventManagement.eventManagement.model.MyUser;


@Repository
public interface UserRepositoryForService extends JpaRepository<MyUser,Long> {
    MyUser findByUsername(String username);
    Optional<MyUser> findByUsernameOrId(String username,Long id);
}
