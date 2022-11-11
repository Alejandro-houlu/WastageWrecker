package com.WastageWreckerBackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WastageWreckerBackend.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    public User getUserByEmail(String email);
    
}
