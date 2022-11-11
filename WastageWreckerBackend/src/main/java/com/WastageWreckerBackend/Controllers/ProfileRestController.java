package com.WastageWreckerBackend.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.WastageWreckerBackend.Config.MyUserDetails;
import com.WastageWreckerBackend.Models.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController {

    @GetMapping("/profile")
    public ResponseEntity<String> getUser(@AuthenticationPrincipal MyUserDetails userDetails){

        if(userDetails == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please sign in");
        }

        User user = userDetails.getUser();

        return ResponseEntity.status(HttpStatus.OK).body(user.toJson().toString());
    }
    
}
