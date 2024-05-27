package com.voter.controller;

import static com.voter.entity.VCList.SEQUENCE_NAME;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voter.entity.VCList;
import com.voter.service.SequenceGeneratorService;
import com.voter.service.VCService;


@RestController
@RequestMapping("/voter/")
public class VCcontroller {
	
	private final Logger LOGGER = 
			LoggerFactory.getLogger(VCcontroller.class);

	@Autowired
	private VCService vcService;
	
	@Autowired
	private SequenceGeneratorService service;

	
	@PostMapping("/addvoter")
	public VCList addvoter(@RequestBody VCList voter){
		
		voter.setId(service.getSequenceNumber(SEQUENCE_NAME));
		return vcService.addvoter(voter);
		
	}
	@GetMapping("/getvoter")
	public List<VCList> getvoter(){
		//LOGGER.info("add method");
	    LOGGER.debug("getVoters method");
				
				
		return vcService.getvoter();
	}
	
	@GetMapping("/id/{id}")
	public Optional<VCList> getvoterById(@PathVariable int id){
		LOGGER.debug("getVoters by id method");
		return vcService.getvoterById(id);
	}
	
	@GetMapping("/name/{name}")
	public List<VCList> getvoterByName(@PathVariable String name){
		
		return vcService.getvoterByName(name);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<VCList> updateVoterById(@PathVariable int id, @RequestBody VCList voter){
		
		return vcService.updateVoterById(id,voter);
	}
	
	@PutMapping("/updateName/{name}")
	public ResponseEntity<VCList> updateVoterByName(@PathVariable String name, @RequestBody VCList voter){
		
		return vcService.updateVoterByName(name,voter);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteVoterById(@PathVariable int id) {
		return vcService.deleteVoterById(id);
	}

	@DeleteMapping("/deleteByName/{name}")
	public String deleteVoterByName(@PathVariable String name, @RequestBody VCList voter) {
		return vcService.deleteVoterByName(name);
	}

	
}
