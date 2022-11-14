package com.WastageWreckerBackend.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WastageWreckerBackend.Models.Promotion;
import com.WastageWreckerBackend.Models.User;
import com.WastageWreckerBackend.Repositories.PromotionRepository;

@Service
public class PromotionImplementation implements PromotionInterface{

    @Autowired
    PromotionRepository promotionRepo;

    @Override
    public void savePromotion(Promotion promotion) {
        
        promotionRepo.save(promotion);

    }

    @Override
    public List<Promotion> getAllPromotionsByAddressId(String addressId) {
        
        return promotionRepo.findAllByAddressAddressId(Long.valueOf(addressId));
    }

    @Override
    public Optional<Promotion> getPromotionbyId(String promotionId) {
        
        return promotionRepo.findById(Long.valueOf(promotionId));
    }

    @Override
    public List<Promotion> getAllPromotionsByOwner(User user) {
        
        return promotionRepo.findAllByOwnerUserId(user.getUserId());
    }
    
}
