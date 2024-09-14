package com.EventManagement.eventManagement.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Events {
    @Id 
    @Column(name = "event_id") 
    @SequenceGenerator( 
            name = "event_sequence",
            sequenceName = "event_sequence",
            allocationSize = 1 
    )
    @GeneratedValue( 
            strategy = GenerationType.SEQUENCE, 
            generator = "event_sequence"
    )
    private Long id;

    private String username;
    private String event_name;
    private String description;
    private LocalDateTime dateField;


    @ElementCollection
    @CollectionTable(name = "event_integers", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "integer_value")
    private List<Long> integers;
}
