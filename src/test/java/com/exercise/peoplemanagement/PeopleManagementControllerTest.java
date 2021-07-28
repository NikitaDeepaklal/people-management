package com.exercise.peoplemanagement;

import com.exercise.peoplemanagement.controller.PeopleManagementController;
import com.exercise.peoplemanagement.exceptions.InvalidInputException;
import com.exercise.peoplemanagement.exceptions.NotFoundException;

import com.exercise.peoplemanagement.model.Person;
import com.exercise.peoplemanagement.service.PeopleManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value= PeopleManagementController.class)
public class PeopleManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeopleManagementService peopleManagementService;

    String jsonMimeType = "application/json";

    Person Bill = new Person("Bill", "Gates");
    Person David = new Person("Hiccup","Dev");

    @Test
    public void getAllPersonsTest_success() throws Exception{
        List<Person> personsList = new ArrayList<>(Arrays.asList(Bill, David));

        Mockito.when(peopleManagementService.getPersons()).thenReturn(personsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/persons").contentType(jsonMimeType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().string(containsString("Bill")));

    }

    @Test
    public void getTotalCountTest_success() throws Exception{
        Mockito.when(peopleManagementService.countPersons()).thenReturn(2L);

        mockMvc.perform(MockMvcRequestBuilders.get("/persons/count").contentType(jsonMimeType))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("2")));
    }

    @Test
    public void AddPersonTest_success() throws Exception{
        Person person = Person.builder().id(1).firstName("Smart1").lastName("Chap").address(null).build();

        Mockito.doReturn(person).when(peopleManagementService).addPerson(person);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/persons").content(asJsonString(person))
                    .contentType(jsonMimeType))
                    .andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void updatePersonTest_success() throws Exception{
        Person updatedPerson = Person.builder().id(1).firstName("Bill").lastName("fla").build();
        String firstName = "Bill";
        String lastName="ive";

        Mockito.when(peopleManagementService.findPersonByName(firstName,lastName)).thenReturn(Bill);
        Mockito.when(peopleManagementService.updatePerson(firstName,lastName, updatedPerson)).thenReturn(updatedPerson);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/persons/Bill-ive")
                .contentType(jsonMimeType)
                .accept(jsonMimeType)
                .content(asJsonString(updatedPerson));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isOk());


    }

    @Test
    public void updatePersonTest_invalidInputs() throws Exception{
        Person updatedPerson = Person.builder().id(1).lastName("fla").build();

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/persons")
                .contentType(jsonMimeType)
                .accept(jsonMimeType)
                .content(asJsonString(updatedPerson));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof InvalidInputException));



    }

    @Test
    public void updatePersonTest_personNotFound() throws Exception{
        Person updatedPerson = Person.builder().id(1).firstName("Bill").lastName("fla").build();

        Mockito.when(peopleManagementService.findPersonByName(updatedPerson.getFirstName(),updatedPerson.getLastName())).thenReturn(null);


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/persons/Bill-fla")
                .contentType(jsonMimeType)
                .accept(jsonMimeType)
                .content(asJsonString(updatedPerson));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof NotFoundException));



    }

    @Test
    public void deletePersonTest_success() throws Exception{
        Mockito.when(peopleManagementService.getPersonById(1)).thenReturn(Bill);

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/persons/1")
                .contentType(jsonMimeType))
                .andExpect(status().isOk());

    }

    @Test
    public void deletePersonTest_notFound() throws Exception{
        Mockito.when(peopleManagementService.getPersonById(3)).thenReturn(null);

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/persons/3")
                .contentType(jsonMimeType))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof NotFoundException));

    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
