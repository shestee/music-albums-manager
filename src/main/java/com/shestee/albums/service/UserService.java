package com.shestee.albums.service;

import com.shestee.albums.dto.UserRegistrationDto;
import com.shestee.albums.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.Valid;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User findByUsername(String name);
    void saveUser(UserRegistrationDto userDto);
}
