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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();

        theModel.addAttribute("userRegistrationDto", userRegistrationDto);

        return "albums/registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
                 @Valid @ModelAttribute("userRegistrationDto") UserRegistrationDto userRegistrationDto,
                 BindingResult theBindingResult,
                 Model theModel) {

        String username = userRegistrationDto.getUsername();

        // form validation
        if (theBindingResult.hasErrors()){
            return "albums/registration-form";
        }

        // check the database if user already exists
        User existing = userService.findByUsername(username);
        if (existing != null){
            theModel.addAttribute("userRegistrationDto", new UserRegistrationDto());
            theModel.addAttribute("registrationError", "User name already exists.");

            return "albums/registration-form";
        }

        // create user account
        userService.saveUser(userRegistrationDto);

        return "albums/registration-confirmation";
    }
}
