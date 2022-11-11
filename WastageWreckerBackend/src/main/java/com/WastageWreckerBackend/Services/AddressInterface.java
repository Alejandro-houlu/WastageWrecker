package com.WastageWreckerBackend.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.WastageWreckerBackend.Models.Address;
import com.WastageWreckerBackend.Models.LocationImages;
import com.WastageWreckerBackend.Models.User;

public interface AddressInterface {
    
    public Optional<Address>getAddress(String addressName);

    public Boolean saveAddress(Address address);

    public Optional<Address> getAddressByPlaceIdAndUser(String placeId, User user);

    public String uploadImageToS3(Address address, MultipartFile outletPic, User user);

    public Boolean saveLocationImage(LocationImages locationImages);

    public List<Address> getAllAddressByUser(User user);

    public List<LocationImages> getLocationImagesByAddress(Address address);

    public Address getAddressByAddressId(String addressId);
}
