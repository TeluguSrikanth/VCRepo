package com.voter.signup.controller;

import static com.voter.entity.VCList.SEQUENCE_NAME;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voter.signup.entity.User;
import com.voter.signup.service.SeqDBGeneratorService;
import com.voter.signup.service.UserService;


@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
    private UserService userService;
	
	@Autowired
	private SeqDBGeneratorService service;

	
    @PostMapping("/signup")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<User> signup(@RequestBody User user) {
    	user.setId(service.getSequenceNumber(SEQUENCE_NAME));
        User newUser = userService.signup(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<User> login(@RequestBody User user) {
    	
        User loggedInUser = userService.login(user.getVotername(), user.getPassword());
        if (loggedInUser != null) {
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    
    @PostMapping("/loginWithOtp")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> loginWithOtp(@RequestBody User user) {
        if (user.getVotername() == null || user.getOtp() == null) {
            return ResponseEntity.badRequest().body("Votername and OTP must be provided");
        }
        boolean isValid = userService.validateOtp(user.getVotername(), user.getOtp());
        if (isValid) {
            // Clear the OTP after successful login
            user.setOtp(null);
            return ResponseEntity.ok(userService.login(user.getVotername(), user.getPassword()));
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
    }
    
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

	
}
