package com.EventManagement.eventManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EventManagement.eventManagement.model.Events;

public interface EventRepository extends JpaRepository<Events,Long>{
    
}
