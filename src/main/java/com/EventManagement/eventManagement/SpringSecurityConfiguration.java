package com.EventManagement.eventManagement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.EventManagement.eventManagement.model.MyUserDetailsService;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Autowired
    MyUserDetailsService myUserDetailsService;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
        .csrf(csrf->csrf.disable())
        .authorizeHttpRequests(request->{
        request.requestMatchers("/home","/register/user","/signup","/**").permitAll();
        request.requestMatchers("/admin/**").hasRole("ADMIN");
        request.requestMatchers("/user/**").hasRole("USER");
        request.anyRequest().authenticated();
    })
        .formLogin(formlogin->formlogin
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .successHandler(new AuthenticationSuccessHandler())
                                .permitAll())
        .logout(logout->logout.logoutSuccessHandler(new CustomLogoutHandler()))
            
        .build();


    }

    // in memory authentication
    // https://bcrypt-generator.com/ for online becrypt password encoder
    //1234 password
    // @Bean
    // public UserDetailsService userDetailsService(){
    //     UserDetails normalUser=User.builder()
    //                                 .username("user")
    //                                 .password("$2a$12$WgamfuC2oeg1tKiM4p/u6OFA99ffketrd9Qgj9l1z84ukpZy2hLaq")
    //                                 .roles("USER")
    //                                 .build();

    //     UserDetails adminUser=User.builder()
    //                                 .username("admin")
    //                                 .password("$2a$12$WgamfuC2oeg1tKiM4p/u6OFA99ffketrd9Qgj9l1z84ukpZy2hLaq")
    //                                 .roles("USER","ADMIN")
    //                                 .build();

    //     return new InMemoryUserDetailsManager(normalUser,adminUser);
    // }

    @Bean
    public UserDetailsService userDetailsService(){
        return myUserDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        //specify type of authentication and password encoder
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(myUserDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
