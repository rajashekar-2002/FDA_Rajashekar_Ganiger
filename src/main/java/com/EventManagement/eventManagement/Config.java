package com.EventManagement.eventManagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.EventManagement.eventManagement.controller.ContentController;

@Configuration
public class Config {
    @Bean
    public ContentController contentController(){
        return new ContentController();
    }
}
