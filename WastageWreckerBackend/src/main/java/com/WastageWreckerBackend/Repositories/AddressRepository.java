package com.WastageWreckerBackend.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.WastageWreckerBackend.Models.Address;
import com.WastageWreckerBackend.Models.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    public Optional<Address> findByPlaceIdAndUser(String placeId, User user);

    public List<Address> findAllByUser(User user);

    public Address findByAddressId(Long addressId);

    String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:lat)) * cos(radians(a.lat)) *" +
        " cos(radians(a.lng) - radians(:lng)) + sin(radians(:lat)) * sin(radians(a.lat))))";

    @Query("SELECT a FROM Address a WHERE " + HAVERSINE_FORMULA + " < :distance ORDER BY " + HAVERSINE_FORMULA + "DESC")
    List<Address> findListsClosest(@Param("lat")Float lat, @Param("lng")Float lng, @Param("distance") double distanceWithinUser);

    
}
