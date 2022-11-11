package com.WastageWreckerBackend.Models;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String addressName;
    private String placeId;
    private Integer postalCode;
    private Float lat;
    private Float lng;
    private String unitNumber;
    private String phoneNumber;

@ManyToOne
   private User user;
   
   @OneToMany(mappedBy = "address")
   private Collection<PromotionalList> promotionalLists;

public Long getAddressId() {
    return addressId;
}

public void setAddressId(Long addressId) {
    this.addressId = addressId;
}

public String getPlaceId() {
    return placeId;
}

public void setPlaceId(String placeId) {
    this.placeId = placeId;
}

public Integer getPostalCode() {
    return postalCode;
}

public void setPostalCode(Integer postalCode) {
    this.postalCode = postalCode;
}

public Float getLat() {
    return lat;
}

public void setLat(Float lat) {
    this.lat = lat;
}

public Float getLng() {
    return lng;
}

public void setLng(Float lng) {
    this.lng = lng;
}

public User getUser() {
    return user;
}

public void setUser(User user) {
    this.user = user;
}

public Collection<PromotionalList> getPromotionalLists() {
    return promotionalLists;
}

public void setPromotionalLists(Collection<PromotionalList> promotionalLists) {
    this.promotionalLists = promotionalLists;
}

public String getAddressName() {
    return addressName;
}

public void setAddressName(String addressName) {
    this.addressName = addressName;
}

@Override
public String toString() {
    return "Address [addressId=" + addressId + ", addressName=" + addressName + ", placeId=" + placeId + ", postalCode="
            + postalCode + ", lat=" + lat + ", lng=" + lng + ", user=" + user + ", promotionalLists=" + promotionalLists
            + "]";
}

public String getUnitNumber() {
    return unitNumber;
}

public void setUnitNumber(String unitNumber) {
    this.unitNumber = unitNumber;
}

public String getPhoneNumber() {
    return phoneNumber;
    }

public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    }

public static Optional<Address> createModel(String resp){

    Address address = new Address();
    DocumentContext jsonContext = JsonPath.parse(resp);

    String checkAddress = jsonContext.read("$.status");
    if(checkAddress.equals("ZERO_RESULTS")){return Optional.empty();}

    List<String> placeId = jsonContext.read("$.*.*.place_id");
    List<Number> lat = jsonContext.read("$.*.*.*.*.lat");
    List<Number> lng = jsonContext.read("$.*.*.*.*.lng");
    List<String> postalCode = jsonContext.read("$.*.*.formatted_address");

    address.setPlaceId(placeId.get(0));
    address.setLat(lat.get(0).floatValue());
    address.setLng(lng.get(0).floatValue());
    try{
    address.setPostalCode( Integer.valueOf(postalCode.get(0).substring(postalCode.get(0).length()-6)) );
    }catch(Exception ex){
        address.setPostalCode(0);
    }
    
    return Optional.of(address);
}



   

    
}
