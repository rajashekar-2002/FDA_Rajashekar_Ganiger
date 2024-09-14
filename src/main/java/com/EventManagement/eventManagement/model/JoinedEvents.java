package com.EventManagement.eventManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JoinedEvents {
    @Id 
    @Column(name = "user_id") 
    @SequenceGenerator( 
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1 
    )
    @GeneratedValue( 
            strategy = GenerationType.SEQUENCE, 
            generator = "user_sequence"
    )
    private Long id;
    private String username;
    // private List<Events> joined;

}
