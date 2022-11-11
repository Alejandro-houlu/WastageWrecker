package com.WastageWreckerBackend.Models;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subId;

    private Timestamp timeSubscribed;
    private Long ownerId;

    @ManyToOne
    private User subscriber;

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public Timestamp getTimeSubscribed() {
        return timeSubscribed;
    }

    public void setTimeSubscribed(Timestamp timeSubscribed) {
        this.timeSubscribed = timeSubscribed;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public User getUser() {
        return subscriber;
    }

    public void setUser(User user) {
        this.subscriber = user;
    }

    @Override
    public String toString() {
        return "Subscription [subId=" + subId + ", timeSubscribed=" + timeSubscribed + ", ownerId=" + ownerId
                + ", subscriber=" + subscriber + "]";
    }
    
}
