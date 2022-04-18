package com.example.choosesector.choosesector.controller;

import com.example.choosesector.choosesector.entity.PersonEntity;
import com.example.choosesector.choosesector.entity.Role;
import com.example.choosesector.choosesector.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(PersonEntity person, Model model){
        PersonEntity userFromDb = personRepository.findByUsername(person.getUsername());
        if(userFromDb != null){
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        if(person.getUsername().isBlank() || person.getPassword().isBlank()){
            model.addAttribute("message", "username or passoword can't will be empty");
            return "registration";
        }
        if(person.isAgree()==false){
            model.addAttribute("message", "you must agree to the terms");
            return "registration";
        }


        person.setAgree(true);
        person.setRoles(Collections.singleton(Role.USER));
        personRepository.save(person);
        return "redirect:/login";
    }
}
