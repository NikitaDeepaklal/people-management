package com.exercise.peoplemanagement.repository;


import com.exercise.peoplemanagement.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
}
