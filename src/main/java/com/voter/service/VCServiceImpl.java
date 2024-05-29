package com.voter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.voter.entity.VCList;
import com.voter.repo.VCRepo;
import com.voter.secure.dto.UserInfi;
import com.voter.secure.repo.UserInfiRepository;

@Service
public class VCServiceImpl implements VCService{

	@Autowired
    private PasswordEncoder passwordEncoder;

	
	@Autowired
	VCRepo vcRepo;
	
	@Autowired
	private UserInfiRepository userInfiRepository;
	
	
	@Override
	public VCList addvoter(VCList voter) {
		
		return vcRepo.save(voter);
	}

	@Override
	public List<VCList> getvoter() {
		
		return vcRepo.findAll();
	}

	@Override
	public Optional<VCList> getvoterById(int id) {
		
		return vcRepo.findById(id);
	}

	@Override
	public List<VCList> getvoterByName(String name) {
		
		return vcRepo.findByName(name);
	}

	@Override
	public ResponseEntity<VCList> updateVoterById(int id, VCList voter) {
		
		VCList vote=vcRepo.findById(id).get();
		vote.setName(voter.getName());
		vote.setAddress(voter.getAddress());
		vote.setAge(voter.getAge());
		VCList updateVoter = vcRepo.save(vote);
		return ResponseEntity.ok(updateVoter);
	}

	@Override
	public ResponseEntity<VCList> updateVoterByName(String name, VCList voter) {
		
		VCList vote1=vcRepo.updateByName(name);
		vote1.setName(voter.getName());
		vote1.setAddress(voter.getAddress());
		vote1.setAge(voter.getAge());
		VCList updateVoter = vcRepo.save(vote1);
		return ResponseEntity.ok(updateVoter);
	}

	@Override
	public String deleteVoterById(int id) {
		
		vcRepo.deleteById(id);
		return "Record deleted Successfully";
	}

	@Override
	public String deleteVoterByName(String name) {
		
		vcRepo.deleteVoterByName(name);
		return "Record deleted Successfully";
	}
	
	public String addUser(UserInfi userInfi) {
        userInfi.setPassword(passwordEncoder.encode(userInfi.getPassword()));
        userInfiRepository.save(userInfi);
        return "user added to system ";
    }
	
	
}


