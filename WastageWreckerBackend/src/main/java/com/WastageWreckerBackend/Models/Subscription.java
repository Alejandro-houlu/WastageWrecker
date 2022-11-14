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

    @ManyToOne
    private User subscriber;

    @ManyToOne
    private Address subscribed;

    
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


    public User getUser() {
        return subscriber;
    }

    public void setUser(User user) {
        this.subscriber = user;
    }
    
    public User getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }

    public Address getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Address subscribed) {
        this.subscribed = subscribed;
    }

    @Override
    public String toString() {
        return "Subscription [subId=" + subId + ", timeSubscribed=" + timeSubscribed + 
                 ", subscriber=" + subscriber + ", subscribed=" + subscribed + "]";
    }


    
}
