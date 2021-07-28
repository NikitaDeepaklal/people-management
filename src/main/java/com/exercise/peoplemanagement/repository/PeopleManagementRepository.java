package com.exercise.peoplemanagement.repository;

import com.exercise.peoplemanagement.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleManagementRepository extends CrudRepository<Person, Integer> {

    @Query(value ="select p from Person p where p.firstName=?1 and p.lastName=?2")
    public Person getPersonByFirstNameAndLastName(String firstName, String lastName);


}
