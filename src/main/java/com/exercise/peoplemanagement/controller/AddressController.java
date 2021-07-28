package com.exercise.peoplemanagement.controller;

import com.exercise.peoplemanagement.exceptions.InvalidInputException;
import com.exercise.peoplemanagement.exceptions.ServiceException;
import com.exercise.peoplemanagement.model.Address;
import com.exercise.peoplemanagement.model.Person;
import com.exercise.peoplemanagement.service.AddressService;
import com.exercise.peoplemanagement.service.PeopleManagementService;
import com.exercise.peoplemanagement.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/persons/{personId}/address")
public class AddressController {

    @Autowired
    PeopleManagementService peopleManagementService;

    @Autowired
    AddressService addressService;

    @PostMapping()
    public void addAddress(@RequestBody Address address, @PathVariable int personId) throws NotFoundException {
        try {
            Person person = peopleManagementService.getPersonById(personId);
            if (person == null)
                throw new NotFoundException("Person id not found! Please retry with different id");

            if (address.getCity() == null|| address.getPostalCode()== 0 || address.getState()==null| address.getStreet()==null)
                throw new InvalidInputException("address info is incomplete");

            person.setAddress(address);
            peopleManagementService.updatePerson(person);
        }
        catch(ServiceException e){
            throw new ServiceException("Error while adding a new address!"+e);
        }
    }

    @PutMapping()
    public void updateAddress(@RequestBody Address address, @PathVariable int personId) throws NotFoundException {
        try{
            Person person =  peopleManagementService.getPersonById(personId);
            if (person == null)
                throw new NotFoundException("Person id not found! Please retry with different id");

            if (address.getCity().isEmpty()|| address.getPostalCode()== 0 || address.getState().isEmpty()| address.getStreet().isEmpty())
                throw new InvalidInputException("address info is incomplete");

            person.setAddress(address);
            peopleManagementService.updatePerson(person);
        }
        catch(ServiceException e){
            throw new ServiceException("Error while updating address!"+e);
        }


    }

    @DeleteMapping()
    public void deleteAddress(@PathVariable int personId) throws NotFoundException{
        try {
            Person person =  peopleManagementService.getPersonById(personId);
            if (person==null || person.getAddress() == null)
                throw new NotFoundException("Person or Address is not found! ");

            addressService.deleteAddressById(person.getAddress().getId());
        }
        catch(ServiceException e){
            throw new ServiceException("Error while deleting address!"+e);
        }

    }
}
