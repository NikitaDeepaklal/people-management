package com.exercise.peoplemanagement.service;

import com.exercise.peoplemanagement.model.Person;
import com.exercise.peoplemanagement.repository.PeopleManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeopleManagementService {

    @Autowired
    PeopleManagementRepository peopleManagementRepository;

    public List<Person> getPersons(){
        List<Person> persons = new ArrayList<>();
        peopleManagementRepository.findAll().forEach(persons::add);
        return persons;
    }

    public Person addPerson(Person person) {
        return peopleManagementRepository.save(person);
    }

    public Person updatePerson(String firstName, String lastName,Person person) {
        Person personToBeUpdated = findPersonByName(firstName,lastName);
        personToBeUpdated.setFirstName(person.getFirstName());
        personToBeUpdated.setFirstName(person.getLastName());
        return peopleManagementRepository.save(personToBeUpdated);

    }

    public Person findPersonByName(String firstName, String lastName){
        return  peopleManagementRepository.getPersonByFirstNameAndLastName(firstName,lastName);

    }

    public void removePerson(int id) {
        peopleManagementRepository.deleteById(id);
    }

    public Person getPersonById(int personId) {
        return peopleManagementRepository.findById(personId).orElse(null);
    }


    public void updatePerson(Person person) {
        peopleManagementRepository.save(person);
    }

    public long countPersons() {
        return peopleManagementRepository.count();
    }
}
