package com.WastageWreckerBackend.Session;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class SessionRegistry {

    private static final HashMap<String, String> SESSIONS = new HashMap<>();

    public String registerSession(String username){
        if(username == null){
            throw new RuntimeException("Username needs to be provided");
        }

        String sessionId = generateSessionId();
        SESSIONS.put(sessionId,username);
        return sessionId;
    }

    public String getUsernameForSession(String sessionId){
        return SESSIONS.get(sessionId);
    }

    private String generateSessionId(){
        return new String(
            Base64.getEncoder().encode(
                UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)
            )
        );
    }
    
}
