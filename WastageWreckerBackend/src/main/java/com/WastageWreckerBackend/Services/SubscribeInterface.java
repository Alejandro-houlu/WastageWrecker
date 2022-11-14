package com.WastageWreckerBackend.Services;


import java.util.List;
import java.util.Optional;

import com.WastageWreckerBackend.Models.Address;
import com.WastageWreckerBackend.Models.Subscription;
import com.WastageWreckerBackend.Models.User;


public interface SubscribeInterface {

    public void saveSubscription(Subscription sub);

    public Optional<Subscription> checkSubscription(Long userId, Long addressId);

    public List<Subscription> getAllUserSubscriptions(Long userId);

    public void unsub(Long userId, Long addressId);
    
}
