package com.exercise.peoplemanagement.service;

import com.exercise.peoplemanagement.model.Address;
import com.exercise.peoplemanagement.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public void deleteAddressById(int addressId){
        addressRepository.deleteById(addressId);

    }

    public Address getAddressById(int addressId){
        return addressRepository.findById(addressId).orElse(null);
    }
}
