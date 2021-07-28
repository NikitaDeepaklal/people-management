package com.exercise.peoplemanagement.controller;

import com.exercise.peoplemanagement.exceptions.InvalidInputException;
import com.exercise.peoplemanagement.exceptions.ServiceException;
import com.exercise.peoplemanagement.model.Person;
import com.exercise.peoplemanagement.service.PeopleManagementService;
import com.exercise.peoplemanagement.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PeopleManagementController {

    @Autowired
    PeopleManagementService peopleManagementService;

    @GetMapping()
    public List<Person> getPersons(){
       return peopleManagementService.getPersons();
    }

    @PostMapping()
    public Person addPerson(@RequestBody Person person){

        try{
            if (person.getFirstName().isEmpty() || person.getLastName().isEmpty())
                throw new InvalidInputException("Json request has invalid customer details. please check the details");

            return peopleManagementService.addPerson(person);
            }
        catch(ServiceException e){
                throw new ServiceException("Error while adding person via the service " + e);
            }



    }

    @PutMapping("/{firstName}-{lastName}")
    public Person updatePerson(@RequestBody Person person, @PathVariable String firstName, @PathVariable String lastName)throws Exception{
        try{
            if ((person.getFirstName().isEmpty()) || (person.getLastName().isEmpty()))
                throw new InvalidInputException("Person Name should not be null!");

            Person personToBeUpdate= peopleManagementService.findPersonByName(firstName,lastName);
            if (personToBeUpdate==null)
                throw new NotFoundException("person with name " + firstName +" " + lastName + " not found");

            return peopleManagementService.updatePerson(firstName, lastName, person);
        }
        catch(ServiceException e){
            throw new ServiceException("Error while updating person via the service " + e.getStackTrace());
        }

    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable int id) throws NotFoundException{
        try{
           Person personToBeRemoved= peopleManagementService.getPersonById(id);
           if (personToBeRemoved==null)
               throw new NotFoundException("Person with id:" +id + "Not found");

           peopleManagementService.removePerson(id);
        }
        catch(ServiceException e){
            throw new ServiceException("Error while deleting person via the service " + e);
        }
        return "Person deleted!";
    }

    @GetMapping("/count")
    public long countPersons(){
        try{
            return peopleManagementService.countPersons();
        }
        catch(ServiceException e){
            throw new ServiceException("Error while counting persons via the service " + e);
        }
    }
}
