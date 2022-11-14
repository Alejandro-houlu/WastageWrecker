package com.WastageWreckerBackend.Models;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

@Entity
public class Promotion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listId;

    private Timestamp created;
    private String itemName;
    private String description;
    private Float price;
    private Date startDate;
    private Time startTime;
    private Time endTime;
    private Date endDate;
    private String promoPicUrl;
    
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Address address;

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }


    public User getUser() {
        return owner;
    }

    public void setUser(User user) {
        this.owner = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getPromoPicUrl() {
        return promoPicUrl;
    }

    public void setPromoPicUrl(String promoPicUrl) {
        this.promoPicUrl = promoPicUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Promotion [listId=" + listId + ", created=" + created + ", itemName=" + itemName + ", description="
                + description + ", price=" + price + ", startDate=" + startDate + ", startTime=" + startTime
                + ", endTime=" + endTime + ", endDate=" + endDate + ", promoPicUrl=" + promoPicUrl + ", status="
                + status + ", owner=" + owner + ", address=" + address + "]";
    }


    


    

    

    

    
}
