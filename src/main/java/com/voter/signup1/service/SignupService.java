package com.voter.signup1.service;

import org.springframework.stereotype.Service;

import com.voter.signup1.dto.SignupDetails;

@Service
public interface SignupService {
	
    void signup(SignupDetails signupDetails);
}
