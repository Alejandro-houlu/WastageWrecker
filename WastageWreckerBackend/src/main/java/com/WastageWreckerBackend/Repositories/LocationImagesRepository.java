package com.WastageWreckerBackend.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WastageWreckerBackend.Models.Address;
import com.WastageWreckerBackend.Models.LocationImages;

@Repository
public interface LocationImagesRepository extends JpaRepository<LocationImages, Long> {

    public List<LocationImages> findByAddress(Address address);
    
}
