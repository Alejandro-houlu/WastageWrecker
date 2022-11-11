package com.WastageWreckerBackend.Services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.WastageWreckerBackend.Models.Address;
import com.WastageWreckerBackend.Models.LocationImages;
import com.WastageWreckerBackend.Models.User;
import com.WastageWreckerBackend.Repositories.AddressRepository;
import com.WastageWreckerBackend.Repositories.LocationImagesRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AddressImplementation implements AddressInterface{

    private String apiUrl = "https://maps.googleapis.com/maps/api";

    @Value("${OPEN_GOOGLE_MAP}")
    String apiKey;

    private String country = "Singapore";

    @Autowired
    AddressRepository addressRepo;

    @Autowired
    LocationImagesRepository locationImagesRepo;

    @Autowired
    AmazonS3 s3;

    @Override
    public Optional<Address> getAddress(String addressName) {

        String url = UriComponentsBuilder.fromUriString(apiUrl)
            .path("/geocode/json")
            .queryParam("address", addressName)
            .queryParam("components","country:" + country)
            .queryParam("key", apiKey)
            .toUriString();

        System.out.println(url);

        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate rTemplate = new RestTemplate();
        ResponseEntity<String> resp = rTemplate.exchange(req, String.class);
        Optional<Address> opt = Address.createModel(resp.getBody());
        
        return opt;
    }

    @Override
    public Boolean saveAddress(Address address) {
        
        try{
            addressRepo.save(address);
        } catch(Exception ex){
            return false;
        }

        return true;
    }

    @Override
    public Optional<Address> getAddressByPlaceIdAndUser(String placeId, User user) {
        return addressRepo.findByPlaceIdAndUser(placeId, user);
        
    }

    @Override
    public String uploadImageToS3(Address address, MultipartFile outletPic, User user) {
        
        String objId = UUID.randomUUID().toString().substring(0,8);
        String bucketName = "alejandrobucket";
        Map<String, String> userCustomMetadata = new HashMap<>();
        userCustomMetadata.put("uploader",address.getAddressName());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(outletPic.getContentType());
        metadata.setContentLength(outletPic.getSize());
        metadata.setUserMetadata(userCustomMetadata);

        try {
            PutObjectRequest putReq = new PutObjectRequest(bucketName,"WastageWrecker/%s/OutletPic/%s".formatted("WastageWrecker "+user.getUsername(), objId), 
            outletPic.getInputStream(), metadata);
            putReq.setCannedAcl(CannedAccessControlList.PublicRead);
            s3.putObject(putReq);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = s3.getUrl(bucketName, "WastageWrecker/%s/OutletPic/%s".formatted("WastageWrecker "+user.getUsername(), objId)).toString();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + url);

        return url;

    }

    @Override
    public Boolean saveLocationImage(LocationImages locationImages) {
        
        try {
            locationImagesRepo.save(locationImages);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public List<Address> getAllAddressByUser(User user) {
        
        return addressRepo.findAllByUser(user);
    }

    @Override
    public List<LocationImages> getLocationImagesByAddress(Address address) {
        
        return locationImagesRepo.findByAddress(address);
    }

    @Override
    public Address getAddressByAddressId(String addressId) {
        
        return addressRepo.findByAddressId(Long.parseLong(addressId));
        
    }
    
}
