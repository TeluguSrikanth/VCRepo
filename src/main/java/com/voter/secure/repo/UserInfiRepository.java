package com.voter.secure.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.voter.secure.dto.UserInfi;

@Repository
public interface UserInfiRepository extends MongoRepository<UserInfi, Integer>{

	Optional<UserInfi> findByName(String username);

	
	
}
