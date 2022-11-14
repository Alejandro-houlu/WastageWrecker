package com.WastageWreckerBackend.Services;

import java.util.List;
import java.util.Optional;

import com.WastageWreckerBackend.Models.Promotion;
import com.WastageWreckerBackend.Models.User;

public interface PromotionInterface {

    public void savePromotion(Promotion promotion);

    public List<Promotion> getAllPromotionsByAddressId(String addressId);

    public Optional<Promotion> getPromotionbyId(String promotionId);

    public List<Promotion> getAllPromotionsByOwner(User user);
    
}
