package com.exercise.peoplemanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public String getRootInfo(){
        return "Welcome to People - Help Page.! Please check the swagger html page for rest endpoint! ";
    }
}
