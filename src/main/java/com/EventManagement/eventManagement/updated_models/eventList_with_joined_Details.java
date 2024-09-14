package com.EventManagement.eventManagement.updated_models;

import com.EventManagement.eventManagement.model.Events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class eventList_with_joined_Details {
    Events event;
    Boolean joined;
}
