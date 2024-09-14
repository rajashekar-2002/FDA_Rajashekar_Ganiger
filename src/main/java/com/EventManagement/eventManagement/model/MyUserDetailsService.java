package com.EventManagement.eventManagement.model;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.EventManagement.eventManagement.Repository.MyUserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private MyUserRepository myUserRepository;


    //search username and return
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user=myUserRepository.findByUsername(username);
        if(user.isPresent()){
            MyUser existuser=user.get();

            //build a userdetail object
            return User.builder()
                .username(existuser.getUsername())
                .password(existuser.getPassword())
                .roles(getRoles(existuser))
                .build();

        }else{
            throw new UsernameNotFoundException(username);
        }
    }


    private String[] getRoles(MyUser existuser) {
        if(existuser.getRole().isEmpty()){
            return new String[]{"USER"};
        }else{
            return existuser.getRole().split(",");
        }
    }
    
}
