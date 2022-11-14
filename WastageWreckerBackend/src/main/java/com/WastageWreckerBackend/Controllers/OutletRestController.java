package com.WastageWreckerBackend.Controllers;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
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
import com.WastageWreckerBackend.Models.Promotion;
import com.WastageWreckerBackend.Models.Status;
import com.WastageWreckerBackend.Models.User;
import com.WastageWreckerBackend.Services.AddressInterface;
import com.WastageWreckerBackend.Services.PromotionInterface;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@RestController
@RequestMapping("/api/outlet")
public class OutletRestController {

    Logger logger = LoggerFactory.getLogger(OutletRestController.class);

    @Autowired
    AddressInterface addressSvc;

    @Autowired
    PromotionInterface promotionSvc;

    @PostMapping("/savePromo")
    public ResponseEntity<String> savePromo(@RequestPart String itemName, @RequestPart String description,
        @RequestPart String startDate, @RequestPart String endDate,
        @RequestPart String status, @RequestPart String price,
        @RequestPart (name="promoPic",required = false)MultipartFile promoPic, @RequestPart String addressId,
        @RequestPart (name="promotionId", required=false) String promotionId, 
        @AuthenticationPrincipal MyUserDetails userDetails){

            User user = userDetails.getUser();
            Promotion promotion = new Promotion();

            if(promotionId != null){
                promotion.setListId(Long.valueOf(promotionId));
                System.out.println(promotion.getListId());}
            promotion.setItemName(itemName);
            promotion.setDescription(description);
            promotion.setStartDate(new Date(Long.valueOf(startDate)));
            promotion.setEndDate(new Date(Long.valueOf(endDate)));
            promotion.setStartTime(new Time(Long.valueOf(startDate)));
            promotion.setEndTime(new Time(Long.valueOf(endDate)));
            promotion.setStatus(Status.valueOf(status));
            promotion.setPrice(Float.valueOf(price));
            promotion.setUser(user);

            Address address = addressSvc.getAddressByAddressId(addressId);
            promotion.setAddress(address);

            promotion.setCreated(new Timestamp(System.currentTimeMillis()));

            if(promoPic!=null){
            String promoPicUrl = addressSvc.uploadImageToS3(address, promoPic, user);
            promotion.setPromoPicUrl(promoPicUrl);
            
            } else{
            Promotion promo2 = promotionSvc.getPromotionbyId(promotionId).get();
            promotion.setPromoPicUrl(promo2.getPromoPicUrl());
            }
            
            logger.info("This is from outlet rest controller");

            try {
                promotionSvc.savePromotion(promotion);
            } catch (Exception e) {
                JsonObject jsonObj = Json.createObjectBuilder()
                .add("message","Save promotion unsuccessful")
                .build();   

                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObj.toString());
            }

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
                .build();   
                

        return ResponseEntity.status(HttpStatus.OK).body(jsonObj.toString());
    }

    @GetMapping("/getAllPromos/{addressId}")
    public ResponseEntity<String> getAllPromo(@PathVariable String addressId){

        List<Promotion> promotions = promotionSvc.getAllPromotionsByAddressId(addressId);

        List<JsonObject> jsonObjList  = new ArrayList<>();

        for(Promotion promotion : promotions ){
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
                .build();

            jsonObjList.add(jsonObj);
        }

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        jsonObjList.stream().forEach(v -> arrBuilder.add(v));

        return ResponseEntity.status(HttpStatus.OK).body(arrBuilder.build().toString());

    }

    
}
