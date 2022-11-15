package com.WastageWreckerBackend.Services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WastageWreckerBackend.Models.Address;
import com.WastageWreckerBackend.Models.Subscription;
import com.WastageWreckerBackend.Models.User;
import com.WastageWreckerBackend.Repositories.SubscriptionRepository;


@Service
public class SubscribeImplementation implements SubscribeInterface {

    @Autowired
    SubscriptionRepository subsRepo;

    @Override
    public void saveSubscription(Subscription sub) {
        
        subsRepo.save(sub);
        
    }

    @Override
    public Optional<Subscription> checkSubscription(Long userId, Long addressId) {
        
        return subsRepo.findBySubscriberUserIdAndSubscribedAddressId(userId, addressId);

        
    }

    @Override
    public List<Subscription> getAllUserSubscriptions(Long userId) {
        
        return subsRepo.findAllBySubscriberUserId(userId);
    }

    @Override
    @Transactional
    public void unsub(Long userId, Long addressId) {
        
        subsRepo.deleteBySubscriberUserIdAndSubscribedAddressId(userId, addressId);
        
    }

    @Override
    public Optional<Subscription> getAllOwnerSubs(Long addressId) {
        
        return subsRepo.findBySubscribedAddressId(addressId);
    }
    
}
