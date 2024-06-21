package com.voter.signup1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voter.signup.entity.User;
import com.voter.signup.repo.UserRepository;
import com.voter.signup.service.UserService;
import com.voter.signup1.dto.SignupDetails;

@Service
public class SignupServiceImpl implements SignupService{

	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private UserService userService; // For OTP generation and email sending, if UserService handles it

	    @Autowired
	    private com.voter.signup.security.AESEncryptionUtil aesEncryptionUtil;
	    
	    @Override
	    public void signup(SignupDetails signupDetails) {
	    	try {
	    		
	    		String encryptedPassword = aesEncryptionUtil.encrypt(signupDetails.getPassword());
	        // Create a new User entity from SignupDetails
	        User user = new User();
	        user.setId(signupDetails.getId());
	        user.setVotername(signupDetails.getVotername());
	      //user.setPassword(signupDetails.getPassword());
	     // Encrypt password before saving
	       // String encryptedPassword = AESEncryptionUtil.encrypt(signupDetails.getPassword());
	        user.setPassword(encryptedPassword);
	        user.setEmail(signupDetails.getEmail());

	        // Save user to database
	        userRepository.save(user);
//	        
//	     // Generate OTP and send it to the provided email
//	        // userService.generateOtp();
//	        // userService.sendOtpServ(signupDetails.getEmail());
//	        
//	     // Generate OTP
//	        String otp = userService.generateOtp();
//
//	        // Send OTP via email
//	        userService.sendOtpServ(signupDetails.getEmail());
//
//	        // Optionally, you can save the generated OTP to the user entity if needed
//	        user.setOtp(otp);
//	        userRepository.save(user);
	        
	        // Generate OTP
	        String otp = userService.generateOtp();

	        // Save the OTP to the user entity
	        user.setOtp(otp);

	        // Save user to database
	        userRepository.save(user);

	        // Send OTP via email
	        userService.sendOtpServ(signupDetails.getEmail(), otp);
	    	
	        
	    } catch (Exception e) {
            // Handle encryption exception
            e.printStackTrace();
        }
	        
}
}


