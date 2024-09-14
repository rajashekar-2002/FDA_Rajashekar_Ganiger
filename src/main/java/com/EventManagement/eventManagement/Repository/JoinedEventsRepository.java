package com.EventManagement.eventManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EventManagement.eventManagement.model.JoinedEvents;


public interface JoinedEventsRepository extends JpaRepository<JoinedEvents,Long> {
        JoinedEvents findByUsername(String username);
}
