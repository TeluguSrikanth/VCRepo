package com.voter.signup.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

@Document(collection = "voter")
public class User {
	
	
	@Id
    private String id;
	@NotBlank(message = "Voter name is Required")
    private String votername;
	@NotBlank(message = "Voter password is Required")
    private String password;
	@NotBlank(message = "Voter mail is Required")
    private String email;
	
	private String otp;
	
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVotername() {
		return votername;
	}
	public void setVotername(String votername) {
		this.votername = votername;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public User(String id, String votername, String password, String email, String otp) {
		super();
		this.id = id;
		this.votername = votername;
		this.password = password;
		this.email = email;
		this.otp=otp;
	}
	
	
}
