package com.voter.signup.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.voter.signup.service.UserService;
import com.voter.signup1.dto.LoginDetails;
import com.voter.signup1.dto.SignupDetails;
import com.voter.signup1.service.SignupService;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private SignupService signupService;

    private ListAppender<ILoggingEvent> listAppender;
    
    

    @BeforeEach
    public void setup() {
        //listAppender = LoggerTestUtils.getListAppenderForClass(AuthController.class);
    }

    @Test
    void testSignup() {
        SignupDetails signupDetails = new SignupDetails();
        signupDetails.setEmail("test@example.com");
        signupDetails.setPassword("password");
        signupDetails.setVotername("testvoter");

        // Mock the void method using doNothing
        doNothing().when(signupService).signup(any(SignupDetails.class));

        ResponseEntity<?> response = authController.signup(signupDetails);

        assertEquals(200, response.getStatusCodeValue(), "Expected HTTP status 200");
        assertEquals("Signup successful. Check your email for verification.", response.getBody(), "Expected response message");

        verify(signupService, times(1)).signup(any(SignupDetails.class));

        // Verify logs
        assertEquals(2, listAppender.list.size(), "Expected two log entries");
        assertEquals("Signup attempt for email: test@example.com", listAppender.list.get(0).getFormattedMessage());
        assertEquals("Signup successful for email: test@example.com", listAppender.list.get(1).getFormattedMessage());
    }

    @Test
    void testLoginWithOtp() {

    	LoginDetails loginDetails = new LoginDetails();

        ResponseEntity<?> response = authController.loginWithOtp(loginDetails);

        // Assert HTTP status 400 (Bad Request) because of missing votername and OTP
        assertEquals(400, response.getStatusCodeValue(), "Expected HTTP status 400");
        assertEquals("Votername and OTP must be provided", response.getBody(), "Expected error message");

        // Verify that userService methods were not called
        verify(userService, never()).validateOtp(any(String.class), any(String.class));
        verify(userService, never()).login(any(String.class));

        // Verify logs (if applicable)
        // Modify assertions based on actual log messages and expected behavior
    
    
    }

    @Test
    void testSignupValidationFails() {
        SignupDetails signupDetails = new SignupDetails(); // invalid details

        ResponseEntity<?> response = authController.signup(signupDetails);

        assertEquals(400, response.getStatusCodeValue(), "Expected HTTP status 400");
        verify(signupService, times(0)).signup(any(SignupDetails.class));
    }

    @Test
    void testLoginWithOtpValidationFails() {
        LoginDetails loginDetails = new LoginDetails(); // invalid details

        ResponseEntity<?> response = authController.loginWithOtp(loginDetails);

        assertEquals(400, response.getStatusCodeValue(), "Expected HTTP status 400");
        assertEquals("Votername and OTP must be provided", response.getBody(), "Expected error message");

        verify(userService, times(0)).validateOtp(any(String.class), any(String.class));
    }

    @Test
    void testLoginWithOtpInvalidOtp() {
        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setVotername("testvoter");
        loginDetails.setOtp("wrongOtp");

        when(userService.validateOtp("testvoter", "wrongOtp")).thenReturn(false);

        ResponseEntity<?> response = authController.loginWithOtp(loginDetails);

        assertEquals(400, response.getStatusCodeValue(), "Expected HTTP status 400");
        assertEquals("Invalid OTP", response.getBody(), "Expected error message");

        verify(userService, times(1)).validateOtp("testvoter", "wrongOtp");
        verify(userService, times(0)).login(any(String.class));
    }
}
