package com.WastageWreckerBackend.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WastageWreckerBackend.Models.Address;
import com.WastageWreckerBackend.Models.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    public Optional<Address> findByPlaceIdAndUser(String placeId, User user);

    public List<Address> findAllByUser(User user);

    public Address findByAddressId(Long addressId);

    
}
