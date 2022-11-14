package com.WastageWreckerBackend.Services;

import java.util.List;

import com.WastageWreckerBackend.Models.Address;

public interface SearchInterface {
    
    public List<Address> findClosestList(Address address, Integer distanceFromUser);
}
