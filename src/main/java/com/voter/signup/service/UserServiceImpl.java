package com.voter.signup.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.voter.exception.ValidationException;
import com.voter.signup.entity.User;
import com.voter.signup.repo.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Validator;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private Validator validator;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	// Define OTP validity duration (in milliseconds)
    private static final long OTP_VALIDITY_DURATION = 5 * 60 * 1000; // 5 minutes


//    @Override
//    public User login(String votername, String password) {
//    	
//    	  // Basic validation: Check if votername and password are not empty
//        if (votername == null || votername.isEmpty() || password == null || password.isEmpty()) {
//            throw new ValidationException("Username and password must be provided");
//        }
//
//        // Check if the user with the provided votername exists
//        User user = userRepository.findByVotername(votername);
//        if (user == null) {
//            throw new ValidationException("User with username '" + votername + "' does not exist");
//        }
//
//        // Validate password (you may have more advanced password validation logic here)
//        if (!user.getPassword().equals(password)) {
//            throw new ValidationException("Incorrect password");
//        }
//        
//     // Login successful, return the user
//        
//        return user;
//        
//        //return userRepository.findByVoternameAndPassword(votername, password);
//    }
//    
       
    @Override
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    public void saveOtpToUser(String Votername, String otp) {
//        User user = userRepository.findByVotername(Votername);
//        if (user == null) {
//            throw new ValidationException("User not found");
//        }
//        user.setOtp(otp);
//        userRepository.save(user);
    	
    	 // Save OTP and current timestamp to the user entity
        User user = userRepository.findByVotername(Votername);
        if (user == null) {
            throw new ValidationException("User not found");
        }
        user.setOtp(otp);
        user.setOtpTimestamp(System.currentTimeMillis()); // Save current timestamp
        userRepository.save(user);
    }
    

    public void sendOtpServ(String email) {
		// TODO Auto-generated method stub
		String otp=generateOtp();
		
		try {
			sendOtp(email,otp);
			
		}catch (MessagingException e) {
			throw new RuntimeException("unable send otp");
		}
		
	}

	private void sendOtp(String email, String otp) throws MessagingException {
		MimeMessage mimeMessage=javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper =new MimeMessageHelper(mimeMessage);
         mimeMessageHelper.setTo(email);
	     mimeMessageHelper.setText(otp);
	     mimeMessageHelper.setSubject("OTP");
	     javaMailSender.send(mimeMessage);
		
	}

	 @Override
	    public User login(String votername) {
	        // Check if the user with the provided votername exists
	        User user = userRepository.findByVotername(votername);
	        if (user == null) {
	            throw new ValidationException("User with username '" + votername + "' does not exist");
	        }

	        // Generate OTP
	       // String otp = generateOtp();

	        // Save OTP to user
	       // saveOtpToUser(votername, otp);

	        // Send OTP via email
	        //sendOtpServ(user.getEmail());

	        // Return the user object
	        return user;
	        
	     // Clear the OTP after successful validation
//		    user.setOtp(null);
//		    userRepository.save(user);
//
//		    // Return the user object
//		    return user;
	        
	    }
	  
	 

	 @Override
	 public boolean validateOtp(String votername, String otp) {
	     // Retrieve the user by votername
	     User user = userRepository.findByVotername(votername);
	     if (user == null) {
	         throw new ValidationException("User not found");
	     }
	     
	     // Check if the stored OTP matches the provided OTP
	     if (user.getOtp() != null && user.getOtp().equals(otp)) {
	         // Clear the OTP after successful validation
	    	// Check OTP expiration
	         
	         user.setOtp(null);
	         userRepository.save(user); // Save the user without OTP
	         return true; // Return true if the OTP is valid
	     
	     }
	     return false; // Return false if the OTP is invalid
	 }
	 

	 @Override
	    public void sendOtpServ(String email, String otp) {
	        try {
	            sendOtp(email, otp);
	        } catch (MessagingException e) {
	            throw new RuntimeException("Unable to send OTP", e);
	        }
	    }
    	
    
//	 @Override
//	    public void sendOtpToEmail(String email) {
//	        // Generate OTP
//	        String otp = generateOtp();
//
//	        // Save OTP to the user
//	        saveOtpToUser(email, otp);
//
//	        // Send OTP via email
//	        sendOtpServ(email, otp);
//	    }
//	 
//	 @Override
//	    public boolean validateOtpByMail(String email, String otp) {
//	    	User voter = userRepository.findByEmail(email);
//	        if (voter == null) {
//	            throw new ValidationException("voter not found");
//	        }
//	        if (voter.getOtp() != null && voter.getOtp().equals(otp)) {
//	        	voter.setOtp(null);
//	        	userRepository.save(voter);
//	            return true;
//	        }
//	        return false;
//	    }
	 
	   
}
