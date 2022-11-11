package com.WastageWreckerBackend.Models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String profilePicUrl;
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToMany(mappedBy = "user")
    private Collection<Address> addresses;

    @OneToMany(mappedBy = "subscriber")
    private Collection<Subscription> subscriptions;

    @OneToMany(mappedBy = "owner")
    private Collection<PromotionalList> promotionLists;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Collection<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Collection<Address> addresses) {
        this.addresses = addresses;
    }

    public Collection<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Collection<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Collection<PromotionalList> getPromotionLists() {
        return promotionLists;
    }

    public void setPromotionLists(Collection<PromotionalList> promotionLists) {
        this.promotionLists = promotionLists;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", email=" + email
                + ", phoneNumber=" + phoneNumber + ", profilePicUrl=" + profilePicUrl + ", enabled=" + enabled
                + ", role=" + role + ", addresses=" + addresses + ", subscriptions=" + subscriptions
                + ", promotionLists=" + promotionLists + "]";
    }

    public JsonObject toJson(){

        return Json.createObjectBuilder()
            .add("userId", userId)
            .add("username",username)
            .add("email", email)
            .add("phoneNumber", phoneNumber)
            .add("profilePicUrl", profilePicUrl)
            .add("role", role.toString())
            .build();
    }



    
    

    


    
}
