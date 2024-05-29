package com.voter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.voter.entity.VCList;
import com.voter.secure.dto.UserInfi;

@Service
public interface VCService {

	VCList addvoter(VCList voter);

	List<VCList> getvoter();

	Optional<VCList> getvoterById(int id);

	List<VCList> getvoterByName(String name);

	ResponseEntity<VCList> updateVoterById(int id, VCList voter);

	ResponseEntity<VCList> updateVoterByName(String name, VCList voter);

	String deleteVoterById(int id);

	String deleteVoterByName(String name);

	String addUser(UserInfi userInfi);
	
}
