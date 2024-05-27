package com.voter.signup.service;

import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voter.exception.ValidationException;
import com.voter.signup.entity.User;
import com.voter.signup.repo.UserRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private Validator validator;

    @Override
    public User signup(User user) {
        // Validation
    	Set<ConstraintViolation<User>> violations = validator.validate(user);
    	if (!violations.isEmpty()) {
            throw new ValidationException("User validation failed");
        }
        return userRepository.save(user);
    }

    @Override
    public User login(String votername, String password) {
    	
    	  // Basic validation: Check if votername and password are not empty
        if (votername == null || votername.isEmpty() || password == null || password.isEmpty()) {
            throw new ValidationException("Username and password must be provided");
        }

        // Check if the user with the provided votername exists
        User user = userRepository.findByVotername(votername);
        if (user == null) {
            throw new ValidationException("User with username '" + votername + "' does not exist");
        }

        // Validate password (you may have more advanced password validation logic here)
        if (!user.getPassword().equals(password)) {
            throw new ValidationException("Incorrect password");
        }
        
     // Login successful, return the user
        return user;
    	
        //return userRepository.findByVoternameAndPassword(votername, password);
    }
    
//    @Override
//    public String generateOtp() {
//        // Generate a random 6-digit OTP
//        Random random = new Random();
//        int otp = 100000 + random.nextInt(900000);
//        return String.valueOf(otp);
//    }
//
//    @Override
//    public void saveOtpToUser(String userId, String otp) {
//        // Update the user with the generated OTP
//        User user = userRepository.findById(userId).orElseThrow(() -> new ValidationException("User not found"));
//        user.setOtp(otp);
//        userRepository.save(user);
//    }
//    
//    @Override
//    public boolean validateOtp(String userId, String otp) {
//        // Validate the OTP for the given user
//        User user = userRepository.findById(userId).orElseThrow(() -> new ValidationException("User not found"));
//        if (user.getOtp() != null && user.getOtp().equals(otp)) {
//            // Clear the OTP after successful validation
//            user.setOtp(null);
//            userRepository.save(user);
//            return true;
//        }
//        return false;
//    }
    
    @Override
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    public void saveOtpToUser(String votername, String otp) {
        User user = userRepository.findByVotername(votername);
        if (user == null) {
            throw new ValidationException("User not found");
        }
        user.setOtp(otp);
        userRepository.save(user);
    }
    
    @Override
    public boolean validateOtp(String votername, String otp) {
        User user = userRepository.findByVotername(votername);
        if (user == null) {
            throw new ValidationException("User not found");
        }
        if (user.getOtp() != null && user.getOtp().equals(otp)) {
            user.setOtp(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }
    
    
}
