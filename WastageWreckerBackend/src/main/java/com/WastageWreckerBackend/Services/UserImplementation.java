package com.WastageWreckerBackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WastageWreckerBackend.Models.User;
import com.WastageWreckerBackend.Repositories.UserRepository;

@Service
public class UserImplementation implements UserInterface{

    @Autowired
    UserRepository userRepo;

    @Override
    public User getUserByEmail(User user) {

       return userRepo.getUserByEmail(user.getEmail());
        
    }
    
}
