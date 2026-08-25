package com.smart.config;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smart.entity.Employee;
import com.smart.entity.User;
import com.smart.helper.SoftwareValidityExpiredException;
import com.smart.repository.EmployeeRepository;
import com.smart.repository.UserRepository;

@Service
public class UserDetailServiceImp implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// fetchin user from data base
		User user = userRepository.getUserByUserName(username);

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
	        	Employee emp = employeeRepository.findEmployeeByEmail(username);
	        	//System.err.println("EXDATE1"+emp.getEmail());
	        	expireDate = dateFormat.parse(emp.getUser().getExpiryDate());
	        	//System.err.println("EXDATE2"+expireDate);
	        }
			//System.err.println(currentDate + " cr:ex " + expireDate);
		} catch (ParseException | java.text.ParseException | NullPointerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		

		if (user == null) {
			throw new UsernameNotFoundException("user not found of email");
		}else if (user != null && "ROLE_ADMIN".equals(user.getRole()) && currentDate.after(expireDate)) {
			//session.setAttribute("message", new com.smart.helper.Message("Software validity is Expired", "danger"));
			throw new SoftwareValidityExpiredException("Your Subcription is Expired, Please Get Subcription");
		}else if (user != null && "ROLE_EMP".equals(user.getRole()) && currentDate.after(expireDate)) {
			//session.setAttribute("message", new com.smart.helper.Message("Software validity is Expired", "danger"));
			throw new SoftwareValidityExpiredException("Your Company Subcription is Expired");
		}else if (user != null && "ROLE_EMP".equals(user.getRole()) && user.isEnabled()== true) {
			//session.setAttribute("message", new com.smart.helper.Message("Software validity is Expired", "danger"));
			throw new SoftwareValidityExpiredException("Access Denied, Please Contact To Your Company");
		}
		
		else if (user != null && "ROLE_EMP".equals(user.getRole()) && currentDate != null && expireDate != null && currentDate.equals(expireDate)) {
		    // Check if user's role is "ROLE_EMP" and the subscription is expired
		    throw new SoftwareValidityExpiredException("Your subscription is expired. Please renew your subscription.");
		}

		CustomUserDetail customUserDetail = new CustomUserDetail(user);

		return customUserDetail;
	}
}
