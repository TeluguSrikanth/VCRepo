package com.voter.signup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.voter.signup.entity.User;
import com.voter.signup.service.UserService;
import com.voter.signup1.dto.LoginDetails;
import com.voter.signup1.dto.SignupDetails;
import com.voter.signup1.service.SignupService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
    private UserService userService;
	
//	@Autowired
//	private SeqDBGeneratorService service;
	
	@Autowired
	private SignupService signupService;

	
//    @PostMapping("/signup")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<User> signup(@RequestBody User user) {
//    	user.setId(service.getSequenceNumber(SEQUENCE_NAME));
//        User newUser = userService.signup(user);
//        return ResponseEntity.ok(newUser);
//    	
//    	 user.setId(service.getSequenceNumber(SEQUENCE_NAME));
//    	    
//    	    // Generate OTP
//    	 String otp = userService.generateOtp();
//    	    
//    	    // Save OTP to user
//    	    userService.saveOtpToUser(user.getEmail(), otp);
//    	    
//    	    // Send OTP via email
//    	    userService.sendOtpEmail(user.getEmail(), otp);
//    	    
//    	    // Save user details
//    	    User newUser = userService.signup(user);
//    	    
//    	    return ResponseEntity.ok(newUser);
    	
    	
//    }

//    @PostMapping("/login")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<User> login(@RequestBody User user) {
//    	
//        User loggedInUser = userService.login(user.getVotername(), user.getPassword());
//        if (loggedInUser != null) {
//            return ResponseEntity.ok(loggedInUser);
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }
    
    @PostMapping("/requestOtp")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> requestOtp(@RequestBody User user) {
        if (user.getVotername() == null) {
            return ResponseEntity.badRequest().body("Votername must be provided");
        }
        String otp = userService.generateOtp();
        userService.saveOtpToUser(user.getVotername(), otp);
        return ResponseEntity.ok("OTP sent to user");
    }
    

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupDetails signupDetails) {
        signupService.signup(signupDetails);
    
        return ResponseEntity.ok("Signup successful. Check your email for verification.");
              
    }
    
    @PostMapping("/loginWithOtp")
    public ResponseEntity<?> loginWithOtp(@RequestBody @Valid LoginDetails loginDetails) {
        if (loginDetails.getVotername() == null || loginDetails.getOtp() == null) {
            return ResponseEntity.badRequest().body("Votername and OTP must be provided");
        }
        
        boolean isValid = userService.validateOtp(loginDetails.getVotername(), loginDetails.getOtp());
        if (isValid) {
            // Clear the OTP after successful login
            User loggedInUser = userService.login(loginDetails.getVotername());
            loggedInUser.setOtp(null);
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
    }

//    @PostMapping("/sendOtpToEmail")
//    public ResponseEntity<String> sendOtpToEmail(@RequestParam("email") String email) {
//        if (email == null) {
//            return ResponseEntity.badRequest().body("Email must be provided");
//        }
//        userService.sendOtpToEmail(email);
//        return ResponseEntity.ok("OTP sent to email");
//    }

	
}
