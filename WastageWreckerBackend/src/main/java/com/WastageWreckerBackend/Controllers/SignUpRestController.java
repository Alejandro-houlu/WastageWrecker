package com.WastageWreckerBackend.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.WastageWreckerBackend.Models.Roles;
import com.WastageWreckerBackend.Models.User;
import com.WastageWreckerBackend.Services.SignupInterface;
import com.WastageWreckerBackend.Services.UserInterface;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class SignUpRestController {

    Logger logger = LoggerFactory.getLogger(SignUpRestController.class);

    @Autowired
    SignupInterface signupSvc;

    @Autowired
    UserInterface userSvc;

    @PostMapping("/signup/saveCustomer")
    public ResponseEntity<String> saveCustomer(
        @RequestPart String username,
        @RequestPart String email,
        @RequestPart String phoneNumber,
        @RequestPart String role,
        @RequestPart String password,
        @RequestPart MultipartFile profilePic){

            logger.info(username);
            logger.info(email);
            logger.info(phoneNumber);
            logger.info(role);
            logger.info(password);
            logger.info(profilePic.getOriginalFilename());

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setPassword(password);
            user.setRole(Roles.valueOf(role));

            if(profilePic.getOriginalFilename().isEmpty()){
                user.setProfilePicUrl("/assets/images/default-avatar.jpg");
            }
            else{
                String imgUrl = signupSvc.uploadImageToS3(user, profilePic);
                user.setProfilePicUrl(imgUrl);
            }

            if(!signupSvc.saveCustomer(user)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Save customer failed");
            }

            User newUser = userSvc.getUserByEmail(user);

            return ResponseEntity.status(HttpStatus.OK).body(newUser.toJson().toString());
        }
    
}
