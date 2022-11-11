package com.WastageWreckerBackend.Services;

import org.springframework.web.multipart.MultipartFile;

import com.WastageWreckerBackend.Models.User;

public interface SignupInterface {
    
    public String uploadImageToS3(User user, MultipartFile profilePic);
    public Boolean saveCustomer(User user);
}
