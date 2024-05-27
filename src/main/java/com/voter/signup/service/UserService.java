package com.voter.signup.service;

import org.springframework.stereotype.Service;

import com.voter.signup.entity.User;

@Service
public interface UserService {

	 User signup(User user);

	 User login(String votername, String password);
	 //User loginBymail(String email, String password);

	
	 String generateOtp();
	 void saveOtpToUser(String votername, String otp);
	 boolean validateOtp(String votername, String otp);
	
	
}
