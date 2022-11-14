package com.WastageWreckerBackend.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.WastageWreckerBackend.Config.MyUserDetails;
import com.WastageWreckerBackend.Models.Address;
import com.WastageWreckerBackend.Models.LocationImages;
import com.WastageWreckerBackend.Models.Promotion;
import com.WastageWreckerBackend.Models.User;
import com.WastageWreckerBackend.Services.AddressInterface;
import com.WastageWreckerBackend.Services.PromotionInterface;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@RestController
@RequestMapping("/api/promotion")
public class PromotionRestController {

    Logger logger = LoggerFactory.getLogger(PromotionRestController.class);
    
    @Autowired
    PromotionInterface promoSvc;

    @Autowired
    AddressInterface addressSvc;

    @GetMapping("/owner/all")
    public ResponseEntity<String>getAllByOwner(@AuthenticationPrincipal MyUserDetails userDetails){

        User user = userDetails.getUser();

        List<Promotion> promoList = promoSvc.getAllPromotionsByOwner(user);
        List<JsonObject> jsonObjList = new ArrayList<>();

        for(Promotion promotion : promoList){

        Address address = promotion.getAddress();
        List<LocationImages> locationImages = addressSvc.getLocationImagesByAddress(address);

        JsonObject jsonObj = Json.createObjectBuilder()
                .add("itemName",promotion.getItemName())
                .add("description", promotion.getDescription())
                .add("startDate", promotion.getStartDate().toString())
                .add("endDate", promotion.getEndDate().toString())
                .add("startTime",promotion.getStartTime().toString())
                .add("endTime", promotion.getEndTime().toString())
                .add("status", promotion.getStatus().toString())
                .add("price", promotion.getPrice().toString())
                .add("promoPicUrl", promotion.getPromoPicUrl())
                .add("promotionId",promotion.getListId())
                .add("addressId", address.getAddressId())
                .add("placeId", address.getPlaceId())
                .add("postalCode", address.getPostalCode())
                .add("storeName", address.getAddressName())
                .add("unitNumber", address.getUnitNumber())
                .add("phoneNumber", address.getPhoneNumber())
                .add("outletPicUrl",locationImages.get(0).getImageUrl())
                .build();

            jsonObjList.add(jsonObj);
        }

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        jsonObjList.stream().forEach(v -> arrBuilder.add(v));

        return ResponseEntity.status(HttpStatus.OK).body(arrBuilder.build().toString());


    }

    @GetMapping("/getAddressById/{promotionId}")
    ResponseEntity<String>getAddressByPromotionId(@PathVariable String promotionId){

        Promotion promotion = promoSvc.getPromotionbyId(promotionId).get();
        Address address = promotion.getAddress();

        List<LocationImages> locationImages = addressSvc.getLocationImagesByAddress(address);

        JsonObject jsonObj = Json.createObjectBuilder()
            .add("itemName",promotion.getItemName())
            .add("description", promotion.getDescription())
            .add("startDate", promotion.getStartDate().toString())
            .add("endDate", promotion.getEndDate().toString())
            .add("startTime",promotion.getStartTime().toString())
            .add("endTime", promotion.getEndTime().toString())
            .add("status", promotion.getStatus().toString())
            .add("price", promotion.getPrice().toString())
            .add("promoPicUrl", promotion.getPromoPicUrl())
            .add("promotionId",promotion.getListId())
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
