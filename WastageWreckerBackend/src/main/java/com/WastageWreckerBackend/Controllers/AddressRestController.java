package com.WastageWreckerBackend.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.WastageWreckerBackend.Config.MyUserDetails;
import com.WastageWreckerBackend.Models.Address;
import com.WastageWreckerBackend.Models.LocationImages;
import com.WastageWreckerBackend.Models.Promotion;
import com.WastageWreckerBackend.Models.User;
import com.WastageWreckerBackend.Services.AddressInterface;
import com.WastageWreckerBackend.Services.PromotionInterface;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@RestController
@RequestMapping("/api/address")
public class AddressRestController {

        Logger logger = LoggerFactory.getLogger(AddressRestController.class);

    @Autowired
    AddressInterface addressSvc;

    @Autowired
    PromotionInterface promotionSvc;

    @PostMapping("/saveAddress")
    @Transactional
    public ResponseEntity<String> saveAddress(@RequestPart String storeName, @RequestPart String unitNumber,
        @RequestPart String phoneNumber, @RequestPart MultipartFile outletPic, @AuthenticationPrincipal MyUserDetails userDetails){

            logger.info(storeName);

            User user = userDetails.getUser();

            Optional<Address> opt = addressSvc.getAddress(storeName.replaceAll("\\s", ""));

            if(opt.isEmpty()){

            JsonObject jsonObj = Json.createObjectBuilder()
                .add("message","Invalid address")
                .build();   
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObj.toString());
            }

            Address address = opt.get();

            Optional<Address>duplicateCheck = addressSvc.getAddressByPlaceIdAndUser(address.getPlaceId(), user);
            if(duplicateCheck.isPresent()){

            JsonObject jsonObj = Json.createObjectBuilder()
                .add("message","Duplicated address")
                .build();   
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObj.toString());

            }

            address.setAddressName(storeName);
            address.setUnitNumber(unitNumber);
            address.setPhoneNumber(phoneNumber);
            address.setUser(user);

            
            if(!addressSvc.saveAddress(address)){

            JsonObject jsonObj = Json.createObjectBuilder()
                .add("message","Save unsuccessful")
                .build();   
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObj.toString()); 
            }


            String outletPicUrl = addressSvc.uploadImageToS3(address, outletPic, user);
            LocationImages locationImages = new LocationImages();
            locationImages.setAddress(address);
            locationImages.setImageUrl(outletPicUrl);
            addressSvc.saveLocationImage(locationImages);


            logger.info(address.getAddressName());
            logger.info(address.getPlaceId());
            logger.info(address.getAddressId().toString());
            logger.info(address.getLat().toString());
            logger.info(address.getLng().toString());
            logger.info(address.getPostalCode().toString());
            logger.info(outletPicUrl);

            JsonObject jsonObj = Json.createObjectBuilder()
                .add("addressId", address.getAddressId())
                .add("placeId", address.getPlaceId())
                .add("postalCode", address.getPostalCode())
                .add("storeName", address.getAddressName())
                .add("outletPicUrl",outletPicUrl)
                .build();   
            return ResponseEntity.status(HttpStatus.OK).body(jsonObj.toString());

        }
    
    @GetMapping("/getAddresses")
    public ResponseEntity<String> getAllAddresses(@AuthenticationPrincipal MyUserDetails userDetails){

        User user = userDetails.getUser();
        List<Address> addresses = addressSvc.getAllAddressByUser(user);

        addresses.stream().forEach(v->System.out.println(v.getAddressName()));

        List<JsonObject> jsonObjList = new ArrayList<>();

        for(Address address: addresses){
            List<LocationImages> locationImages = addressSvc.getLocationImagesByAddress(address);

            List<Promotion> promos = promotionSvc.getAllPromotionsByAddressId(address.getAddressId().toString());

            JsonObject jsonObj = Json.createObjectBuilder()
                .add("addressId", address.getAddressId())
                .add("placeId", address.getPlaceId())
                .add("postalCode", address.getPostalCode())
                .add("storeName", address.getAddressName())
                .add("unitNumber", address.getUnitNumber())
                .add("outletPicUrl",locationImages.get(0).getImageUrl())
                .add("numberOfPromos", promos.size())
                .build();

            jsonObjList.add(jsonObj);
        }

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        
        jsonObjList.stream().forEach(v -> arrBuilder.add(v));

        return ResponseEntity.status(HttpStatus.OK).body(arrBuilder.build().toString());
    }

    @GetMapping("/getAddress/{addressId}")
    public ResponseEntity<String> getAddressByAddressId(@PathVariable String addressId){

        Address address = addressSvc.getAddressByAddressId(addressId);
        List<LocationImages> locationImages = addressSvc.getLocationImagesByAddress(address);

        JsonObject jsonObj = Json.createObjectBuilder()
            .add("addressId", address.getAddressId())
            .add("placeId", address.getPlaceId())
            .add("postalCode", address.getPostalCode())
            .add("storeName", address.getAddressName())
            .add("unitNumber", address.getUnitNumber())
            .add("phoneNumber", address.getPhoneNumber())
            .add("outletPicUrl",locationImages.get(0).getImageUrl())
            .build();
        
        
        return ResponseEntity.status(HttpStatus.OK).body(jsonObj.toString());

    }
    
}
