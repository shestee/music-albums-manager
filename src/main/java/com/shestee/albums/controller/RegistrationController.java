package com.shestee.albums.controller;

import com.shestee.albums.dto.UserRegistrationDto;
import com.shestee.albums.entity.User;
import com.shestee.albums.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showRegistrationForm(Model theModel) {
        theModel.addAttribute("userDto", new UserRegistrationDto());

        return "albums/registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("userDto") UserRegistrationDto theUserDto,
            BindingResult theBindingResult,
            Model theModel) {

        String username = theUserDto.getUsername();

        // form validation
        if (theBindingResult.hasErrors()){
            return "registration-form";
        }

        // check the database if user already exists
        User existing = userService.findByUsername(username);
        if (existing != null){
            theModel.addAttribute("userDto", new UserRegistrationDto());
            theModel.addAttribute("registrationError", "User name already exists.");

            return "registration-form";
        }

        // create user account
        userService.saveUser(theUserDto);

        return "albums/registration-confirmation";
    }
}
