package com.smart.helper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.smart.entity.User;
import com.smart.repository.UserRepository;

@Service
public class CurrentUserService {
	
	@Autowired
    private UserRepository userRepository;

    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.getUserByUserName(email);
    }

}
