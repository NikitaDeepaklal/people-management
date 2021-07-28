package com.exercise.peoplemanagement;


import com.exercise.peoplemanagement.controller.AddressController;
import com.exercise.peoplemanagement.controller.PeopleManagementController;
import com.exercise.peoplemanagement.exceptions.InvalidInputException;
import com.exercise.peoplemanagement.exceptions.NotFoundException;
import com.exercise.peoplemanagement.model.Address;
import com.exercise.peoplemanagement.model.Person;
import com.exercise.peoplemanagement.service.AddressService;
import com.exercise.peoplemanagement.service.PeopleManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value= AddressController.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    AddressService addressService;

    @MockBean
    PeopleManagementService peopleManagementService;

    String jsonMimeType = "application/json";

    Address address = new Address("no 5", "New", "state", 12345);
    Address invalidAddress = new Address("", "","",0);
    Person person = new Person(1,"Bill", "last", address);
    Person noAddressPerson = new Person(1,"Bill","last", null);

    @Test
    public void deleteAddressTest_success() throws Exception{

        Mockito.when(peopleManagementService.getPersonById(1)).thenReturn(person);

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/persons/1/address")
                .contentType(jsonMimeType))
                .andExpect(status().isOk());

    }

   @Test
    public void deleteAddressTest_notFound() throws Exception{
       Mockito.when(peopleManagementService.getPersonById(3)).thenReturn(null);

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/persons/3/address")
                .contentType(jsonMimeType))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof NotFoundException));

    }

    @Test
    public void updateAddressTest_success() throws Exception{
        Mockito.when(peopleManagementService.getPersonById(1)).thenReturn(noAddressPerson);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/persons/1/address")
                .contentType(jsonMimeType)
                .accept(jsonMimeType)
                .content(this.mapper.writeValueAsString(address));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isOk());

    }

    @Test
    public void updateAddressTest_notFound() throws Exception{
        //Mockito.when(peopleManagementService.getPersonById(1)).thenReturn(noAddressPerson);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/persons/1/address")
                .contentType(jsonMimeType)
                .accept(jsonMimeType)
                .content(this.mapper.writeValueAsString(address));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof NotFoundException));

    }

    @Test
    public void updateAddressTest_invalidInput() throws Exception{
        Mockito.when(peopleManagementService.getPersonById(1)).thenReturn(noAddressPerson);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/persons/1/address")
                .contentType(jsonMimeType)
                .accept(jsonMimeType)
                .content(this.mapper.writeValueAsString(invalidAddress));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof InvalidInputException));


    }

    @Test
    public void addAddressTest_success() throws Exception{
        Mockito.when(peopleManagementService.getPersonById(1)).thenReturn(noAddressPerson);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/persons/1/address")
                .contentType(jsonMimeType)
                .accept(jsonMimeType)
                .content(this.mapper.writeValueAsString(address));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isOk());

    }

    @Test
    public void addAddressTest_notFound() throws Exception{

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/persons/1/address")
                .contentType(jsonMimeType)
                .accept(jsonMimeType)
                .content(this.mapper.writeValueAsString(address));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof NotFoundException));

    }

    @Test
    public void addAddressTest_invalidInput() throws Exception{
        Mockito.when(peopleManagementService.getPersonById(1)).thenReturn(noAddressPerson);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/persons/1/address")
                .contentType(jsonMimeType)
                .accept(jsonMimeType)
                .content(this.mapper.writeValueAsString(invalidAddress));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof InvalidInputException));


    }

}
