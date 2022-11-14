package com.WastageWreckerBackend.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WastageWreckerBackend.Models.Address;
import com.WastageWreckerBackend.Repositories.AddressRepository;

@Service
public class SearchImplementation implements SearchInterface{

    @Autowired
    AddressRepository addressRepo;

    @Override
    public List<Address> findClosestList(Address address, Integer distanceWithinUser) {
        
        List<Address> resultList = addressRepo.findListsClosest(address.getLat(), address.getLng(), distanceWithinUser);

        return resultList;
    }
    
}
