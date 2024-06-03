package com.voter.signup1.dto;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotBlank;

public class LoginDetails {
	
	@Id
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	 @NotBlank(message = "Voter name is required")
	    private String votername;

	    @NotBlank(message = "OTP is required")
	    private String otp;

		public String getVotername() {
			return votername;
		}

		public void setVotername(String votername) {
			this.votername = votername;
		}

		public String getOtp() {
			return otp;
		}

		public void setOtp(String otp) {
			this.otp = otp;
		}
}
