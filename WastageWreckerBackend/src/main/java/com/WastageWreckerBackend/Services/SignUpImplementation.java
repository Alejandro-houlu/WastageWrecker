package com.WastageWreckerBackend.Services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.WastageWreckerBackend.Models.User;
import com.WastageWreckerBackend.Repositories.UserRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class SignUpImplementation implements SignupInterface{

    @Autowired
    AmazonS3 s3;

    @Autowired
    UserRepository userRepo;

    @Override
    public String uploadImageToS3(User user, MultipartFile profilePic) {
        
        String objId = UUID.randomUUID().toString().substring(0,8);
        String bucketName = "alejandrobucket";
        Map<String, String> userCustomMetadata = new HashMap<>();
        userCustomMetadata.put("uploader",user.getUsername());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(profilePic.getContentType());
        metadata.setContentLength(profilePic.getSize());
        metadata.setUserMetadata(userCustomMetadata);

        try {
            PutObjectRequest putReq = new PutObjectRequest(bucketName,"WastageWrecker/%s/ProfilePic/%s".formatted("WastageWrecker "+user.getUsername(), objId), 
            profilePic.getInputStream(), metadata);
            putReq.setCannedAcl(CannedAccessControlList.PublicRead);
            s3.putObject(putReq);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = s3.getUrl(bucketName, "WastageWrecker/%s/ProfilePic/%s".formatted("WastageWrecker "+user.getUsername(), objId)).toString();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + url);

        return url;

    }

    @Override
    public Boolean saveCustomer(User user) {
        String rawPassword = user.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        user.setEnabled(true);

        try{
            userRepo.save(user);
        }catch(Exception ex){
            return false;
        }
        return true;
    }
    
}
