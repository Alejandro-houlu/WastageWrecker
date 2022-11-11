package com.WastageWreckerBackend.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    
    private String itemName;
    private String itemPicUrl;

    @ManyToOne
    private PromotionalList promotionalList;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPicUrl() {
        return itemPicUrl;
    }

    public void setItemPicUrl(String itemPicUrl) {
        this.itemPicUrl = itemPicUrl;
    }

    public PromotionalList getPromotionalList() {
        return promotionalList;
    }

    public void setPromotionalList(PromotionalList promotionalList) {
        this.promotionalList = promotionalList;
    }

    @Override
    public String toString() {
        return "Item [itemId=" + itemId + ", itemName=" + itemName + ", itemPicUrl=" + itemPicUrl + ", promotionalList="
                + promotionalList + "]";
    }

    
}
