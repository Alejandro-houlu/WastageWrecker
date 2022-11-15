package com.WastageWreckerBackend.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WastageWreckerBackend.Models.Address;
import com.WastageWreckerBackend.Models.Subscription;
import com.WastageWreckerBackend.Models.User;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    public Optional<Subscription> findBySubscriberUserIdAndSubscribedAddressId(Long userId, Long addressId);

    public List<Subscription> findAllBySubscriberUserId(Long userId);

    public void deleteBySubscriberUserIdAndSubscribedAddressId(Long userId, Long addressId);

    public Optional<Subscription> findBySubscribedAddressId(Long addressID);


    
}
