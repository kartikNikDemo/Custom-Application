package com.smart.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smart.config.UserDetailServiceImp;
import com.smart.dto.ErrorMessage;
import com.smart.dto.UserDto;
import com.smart.entity.AuthRequest;
import com.smart.entity.Employee;
import com.smart.entity.User;
import com.smart.helper.SoftwareValidityExpiredException;
import com.smart.repository.EmployeeRepository;
import com.smart.repository.UserRepository;
import com.smart.service.JwtUtil;

@Controller
@RestController
public class HomeController {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
	private UserDetailServiceImp userDetailServiceImp;
    
    @Autowired
	private UserRepository userRepository;
    
    @Autowired
	private EmployeeRepository employeeRepository;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    

    @GetMapping("/")
    public String welcome() {
        return "Welcome to Home !!";
    }
    
    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }
    
	/*
	 * @GetMapping("/login") public String login() { return "login"; }
	 */

    @PostMapping("/signin")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            
            
            final UserDetails userDetails = userDetailServiceImp.loadUserByUsername(authRequest.getUsername());
           
            User user = userRepository.getUserByUserName(authRequest.getUsername());
            
            LocalDateTime currentDateTime = LocalDateTime.now();
    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    		String date1 = currentDateTime.format(formatter);

    		// Define date format
    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		Date expireDate = null;
    		Date currentDate = null;
    		try {
    			currentDate = dateFormat.parse(date1);
    			if(user.getRole().equals("ROLE_ADMIN")) {
    			expireDate = dateFormat.parse(user.getExpiryDate());
    			}
    			if(user.getRole().equals("ROLE_EMP")) {
    	        	Employee emp = employeeRepository.findEmployeeByEmail(authRequest.getUsername());
    	        	//System.err.println("EXDATE"+emp.getEmail());
    	        	expireDate = dateFormat.parse(emp.getUser().getExpiryDate());
    	        	//System.err.println("EXDATE"+expireDate);
    	        }
    			//System.err.println(currentDate + " : " + expireDate);
    		} catch (ParseException | java.text.ParseException | NullPointerException e) {
    			// TODO Auto-generated catch block
    			//e.printStackTrace();
    		}
    		
    		if (user == null) {
    			//session.setAttribute("message", new com.smart.helper.Message("user not found of email", "danger"));
    			return ResponseEntity.ok(new ErrorMessage("user not found of email or invalid credential"));
    		}
            
            if (user != null && "ROLE_ADMIN".equals(user.getRole()) && currentDate.after(expireDate)) {
    			//session.setAttribute("message", new com.smart.helper.Message("Software validity is Expired", "danger"));
            	return ResponseEntity.ok(new ErrorMessage("Your Subcription is Expired, Please Get Subcription"));
    		}
            
            if(user.getRole().equals("ROLE_EMP") && user.isEnabled()== true) {
            	
            	 return ResponseEntity.ok(new ErrorMessage("Access Denied, Please Contact To Your Company",HttpStatus.BAD_REQUEST));
            }
            if (user != null && "ROLE_EMP".equals(user.getRole()) && currentDate != null && expireDate != null && currentDate.equals(expireDate)) {
    		    // Check if user's role is "ROLE_EMP" and the subscription is expired
            	return ResponseEntity.ok(new ErrorMessage("Your subscription is expired. Please renew your subscription."));
    		}
            final String token = jwtUtil.generateToken(user.getId(), userDetails);
            UserDto uDto = null;
            
            
            
            
            if(user.getRole().equals("ROLE_ADMIN")) {
            	uDto = new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole(),user.isEnabled(), user.getEmailAccess(), user.getIntegrationAccess(), user.getTrackingAccess(),user.getLeadDashboardAccess(),user.getTaskDashboardAccess(),user.getPipelineAccess(),user.getPlanName(), user.getTimeZone(), user.getExpiryDate(),"",0,0,user.getCompanyName(),user.getCompanyPhone(),user.getCompanyAddress(),user.getGSTIN(),user.getCompanyEmail(), token,user.getCustomDashboardAccess(),user.getAccessAcordingShiftTime(),user.getMaxEmployee());
            }else if(user.getRole().equals("ROLE_EMP")){
            	Employee emp = employeeRepository.findEmployeeByEmail(user.getEmail());
            	uDto = new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole(),user.isEnabled(), emp.getEmailAccess(), emp.getIntegrationAccess(), emp.getTrackingAccess(),emp.getLeadDashboardAccess(),emp.getTaskDashboardAccess(),emp.getPipelineAccess(),emp.getUser().getPlanName(), emp.getTimeZone(), emp.getUser().getExpiryDate(),user.getEmpStatus(),emp.getcId(),emp.getUser().getId(),emp.getUser().getCompanyName(),emp.getUser().getCompanyPhone(),emp.getUser().getCompanyAddress(),emp.getUser().getGSTIN(),emp.getUser().getCompanyEmail(), token,emp.getUser().getLogioImage(),emp.getSignImage(),emp.getShiftTime(),emp.getPhone(),emp.getWork(),emp.getCustomDashboardAccess(),emp.getAccessAcordingShiftTime(),emp.getEmailForwardAccess());
            }
            return ResponseEntity.ok(uDto);
        }catch (SoftwareValidityExpiredException s) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your subscription is expired. Please renew your subscription.");
        } catch (UsernameNotFoundException u) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (AuthenticationException ex) {
        	//ex.printStackTrace();
        	//System.err.println("Error :" + ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during authentication: " + e.getMessage());
        }
    }
    


}
