package com.WastageWreckerBackend.Controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.WastageWreckerBackend.Config.MyUserDetails;
import com.WastageWreckerBackend.Models.Address;
import com.WastageWreckerBackend.Models.Promotion;
import com.WastageWreckerBackend.Models.Subscription;
import com.WastageWreckerBackend.Models.User;
import com.WastageWreckerBackend.Services.AddressInterface;
import com.WastageWreckerBackend.Services.SubscribeInterface;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@RestController
@RequestMapping("/api")
public class SuscriptionRestController {

    @Autowired
    AddressInterface addressSvc;

    @Autowired
    SubscribeInterface subSvc;

    @PostMapping("/subscribe")
    ResponseEntity<String> suscribe(@RequestBody String addressId, @AuthenticationPrincipal MyUserDetails userDetails){

        User user = userDetails.getUser();
        Address address = addressSvc.getAddressByAddressId(addressId);

        Subscription sub = new Subscription();
        sub.setSubscriber(user);
        sub.setSubscribed(address);
        sub.setTimeSubscribed(new Timestamp(System.currentTimeMillis()));
        subSvc.saveSubscription(sub);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @GetMapping("/isSubscribed/{addressId}")
    ResponseEntity<String> isSubscribed(@PathVariable String addressId, @AuthenticationPrincipal MyUserDetails userDetails){
        User user = userDetails.getUser();

        Optional<Subscription> sub = subSvc.checkSubscription(user.getUserId(), Long.valueOf(addressId));


        if(sub.isEmpty()){

            JsonObject jsonObj = Json.createObjectBuilder()
                .add("isSubbed", "false")
                .build();

            return ResponseEntity.status(HttpStatus.OK).body(jsonObj.toString());
        }

        JsonObject jsonObj = Json.createObjectBuilder()
            .add("isSubbed", "true")
            .build();

        return ResponseEntity.status(HttpStatus.OK).body(jsonObj.toString());

    }

    @GetMapping("/getAllUserSubs")
    public ResponseEntity<String> getAllUserSubs(@AuthenticationPrincipal MyUserDetails userDetails){

        User user = userDetails.getUser();

        List<Subscription> subscriptionList = subSvc.getAllUserSubscriptions(user.getUserId());

        List<Promotion> subscribedPromos = new ArrayList<>();
        
        subscriptionList.stream().forEach(v->{
            subscribedPromos.addAll(v.getSubscribed().getPromotionalLists());
        });

        System.out.println("from getAllUserSubs Controller>>>>>");
        subscribedPromos.stream().forEach(v-> System.out.println(v.getItemName()));

        List<JsonObject> jsonObjList = new ArrayList<>();

        for(Promotion promotion : subscribedPromos){
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
            .add("addressId", promotion.getAddress().getAddressId())
            .add("placeId", promotion.getAddress().getPlaceId())
            .add("postalCode", promotion.getAddress().getPostalCode())
            .add("storeName", promotion.getAddress().getAddressName())
            .add("unitNumber", promotion.getAddress().getUnitNumber())
            .add("phoneNumber", promotion.getAddress().getPhoneNumber())
            .add("outletPicUrl", promotion.getAddress().getLocationImages().iterator().next().getImageUrl())
            .add("lat", promotion.getAddress().getLat())
            .add("lng", promotion.getAddress().getLng())
            .build();

            jsonObjList.add(jsonObj);
        }

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        jsonObjList.stream().forEach(v -> arrBuilder.add(v));

        return ResponseEntity.status(HttpStatus.OK).body(arrBuilder.build().toString());

    }

    @GetMapping("/getAllOwnerSubs")
    public ResponseEntity<String> getAllOwnerSubs(@AuthenticationPrincipal MyUserDetails userDetails){

        User user = userDetails.getUser();
        List<Address> outlets = addressSvc.getAllAddressByUser(user);
        List<Subscription> ownerSubs = new ArrayList<>();

        for(Address address: outlets){
            
            Optional<Subscription> optSub = subSvc.getAllOwnerSubs(address.getAddressId());
            if(optSub.isPresent()){
                ownerSubs.add(optSub.get());
            }

        }
    
        JsonObject jsonObj = Json.createObjectBuilder()
            .add("ownerSubs", ownerSubs.size())
            .build();
        
        return ResponseEntity.status(HttpStatus.OK).body(jsonObj.toString());

    }

    @DeleteMapping("/unsub/{addressId}")
    ResponseEntity<String> unsubscribe(@PathVariable String addressId, @AuthenticationPrincipal MyUserDetails userDetails){

        User user = userDetails.getUser();
        subSvc.unsub(user.getUserId(), Long.valueOf(addressId));

        JsonObject jsonObj = Json.createObjectBuilder()
            .add("message", "Delete Successful")
            .build();

        return ResponseEntity.status(HttpStatus.OK).body(jsonObj.toString());
    }
    
}
