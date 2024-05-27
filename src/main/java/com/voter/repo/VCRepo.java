package com.voter.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.voter.entity.VCList;

@Repository
public interface VCRepo extends MongoRepository<VCList, Integer>{

	List<VCList> findByName(String name);

	@Query("{ 'name' : ?0 }")
	VCList updateByName(String name);

	void deleteVoterByName(String name);

	

}
