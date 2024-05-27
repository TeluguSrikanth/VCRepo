package com.voter.signup.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.voter.signup.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	User findByVoternameAndPassword(String votername, String password);

	User findByVotername(String votername);
	
	User findByOtp(String otp);
//	void updateOtp(String userId, String otp);

	
}
