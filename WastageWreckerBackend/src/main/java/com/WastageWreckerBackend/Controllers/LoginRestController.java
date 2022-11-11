package com.WastageWreckerBackend.Controllers;

import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.WastageWreckerBackend.Config.MyUserDetails;
import com.WastageWreckerBackend.Models.User;
import com.WastageWreckerBackend.Models.LoginDTO;
import com.WastageWreckerBackend.Session.SessionRegistry;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginRestController {

    @Autowired
    AuthenticationManager manager;
    @Autowired
    SessionRegistry sessionRegistry;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){

        // headers.forEach((key, value) -> {
        //     System.out.println(String.format("Header '%s' = %s", key, value));
        // });

        MyUserDetails userDetails = (MyUserDetails) manager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())).getPrincipal();

        String sessionId = sessionRegistry.registerSession(loginDTO.getEmail());

        System.out.println(userDetails.getUsername());

        System.out.println(loginDTO.getEmail() + loginDTO.getPassword());


        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // System.out.println(authentication.getPrincipal());
		//  if(authentication == null|| authentication instanceof AnonymousAuthenticationToken) {
		//  	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
		//  }

        //  MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        //  User user = userDetails.getUser();

        //  JsonObject jsonobj = Json.createObjectBuilder()
        //     .add("userId", user.getUserId())
        //     .add("email", user.getEmail())
        //     .add("phoneNumber",user.getPhoneNumber())
        //     .add("role",user.getRole().toString())
        //     .add("username",user.getUsername())
        //     .build();

        JsonObject jsonObj = Json.createObjectBuilder()
            .add("sessionId",sessionId)
            .add("userId", userDetails.getUserId())
            .add("role",userDetails.getRole().toString())
            .build();
         
        
		return ResponseEntity.status(HttpStatus.OK).body(jsonObj.toString());

    }
    
}
