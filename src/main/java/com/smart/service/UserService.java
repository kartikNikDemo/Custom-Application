package com.smart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smart.dto.request.CreateEmployee;
import com.smart.entity.Company;
import com.smart.entity.User;
import com.smart.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(String username, String rawPassword) {
        User user = new User();
        user.setUsername(username);
        user.setRole("ROLE_ADMIN");
        user.setExpiryDate("2027-11-08");
        user.setPassword(passwordEncoder.encode(rawPassword)); // Hash the password
        userRepository.save(user); // Save user to database
    }
    
    public User saveAdminUser(Company company) {
        User user = new User();
        user.setUsername(company.getLoginEmail());
        user.setRole("ROLE_ADMIN");
        user.setExpiryDate("2027-11-08");
        user.setCompanyId(company.getId());
        user.setPassword(passwordEncoder.encode("Admin@123")); // Hash the password
     return    userRepository.save(user); // Save user to database
    }
    
    public User createEmployeeUser(CreateEmployee createEmployee) {
        User user = new User();
        user.setUsername(createEmployee.getUsername());
        user.setRole("ROLE_EMPLOYEE");
        user.setCompanyId(createEmployee.getCompanyId());
        user.setExpiryDate("2027-11-08");
        user.setPassword(passwordEncoder.encode(createEmployee.getPassword())); // Hash the password
     return   userRepository.save(user); // Save user to database
    }
}