package com.WastageWreckerBackend.Controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.WastageWreckerBackend.Models.Address;
import com.WastageWreckerBackend.Models.Promotion;
import com.WastageWreckerBackend.Services.AddressInterface;
import com.WastageWreckerBackend.Services.SearchInterface;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@RestController
@RequestMapping("/api/search")
public class SearchRestController {

    @Autowired
    AddressInterface addressSvc;

    @Autowired
    SearchInterface searchSvc;

    @PostMapping("/")
    ResponseEntity<String> searchAddress(@RequestBody String searchAddress){

        Integer distance = 8;

        Optional<Address> opt = addressSvc.getAddress(searchAddress.replaceAll("\\s", ""));

            if(opt.isEmpty()){

            JsonObject jsonObj = Json.createObjectBuilder()
                .add("message","Invalid address")
                .build();   
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObj.toString());
            }
        List<Address> closestAddress = searchSvc.findClosestList(opt.get(), distance);
        List<Promotion> closestActivePromotions = new ArrayList<>();

        closestAddress.stream().forEach(v -> System.out.println(v.getAddressName()));

        List<Promotion> tempPromoList = new ArrayList<>();
        closestAddress.stream().forEach(v->tempPromoList.addAll(v.getPromotionalLists()));

        tempPromoList.stream().filter(v->v.getStatus().toString() == "ACTIVE").forEach(v->closestActivePromotions.add(v));
        closestActivePromotions.stream().forEach(v->System.out.println(v.getAddress().getAddressName() + v.getItemName()));

        List<JsonObject> jsonObjList = new ArrayList<>();

        for(Promotion promotion : closestActivePromotions){
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
            .add("searchedAddLat", opt.get().getLat())
            .add("searchedAddLng", opt.get().getLng())
            .build();

            jsonObjList.add(jsonObj);
        }

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        jsonObjList.stream().forEach(v -> arrBuilder.add(v));


        return ResponseEntity.status(HttpStatus.OK).body(arrBuilder.build().toString());
    }
    
}
