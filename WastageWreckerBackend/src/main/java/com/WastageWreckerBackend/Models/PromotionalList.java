package com.WastageWreckerBackend.Models;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class PromotionalList {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listId;

    private Timestamp created;
    private String description;
    private Float price;
    private Date endDate;
    private Time endTime;

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "promotionalList")
    private Collection<Item> items;

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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public User getUser() {
        return owner;
    }

    public void setUser(User user) {
        this.owner = user;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "PromotionalList [listId=" + listId + ", created=" + created + ", description=" + description
                + ", price=" + price + ", endDate=" + endDate + ", endTime=" + endTime + ", owner=" + owner + ", items="
                + items + ", address=" + address + "]";
    }

    

    
}
