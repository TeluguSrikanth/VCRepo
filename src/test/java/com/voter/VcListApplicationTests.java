package com.voter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.voter.controller.VCcontroller;
import com.voter.entity.VCList;
import com.voter.service.VCService;

@SpringBootTest
class VcListApplicationTests {
	
	private final Logger LOGGER = 
			LoggerFactory.getLogger(VcListApplicationTests.class);

	@InjectMocks
	private VCcontroller vccontroller;
	
	@Mock
	private VCService vcService;
	
	@Test
    public void testAddVoter() {
		LOGGER.info("Starting testServiceMethod");
        VCList voter = new VCList(1, "Srikanth", "Hyderabad", 24);
        when(vcService.addvoter(any(VCList.class))).thenReturn(voter);

        VCList result = vcService.addvoter(voter);
        LOGGER.info("Starting testServiceMethod");

        assertNotNull(result, "The saved voter should not be null");
        assertEquals(voter, result);
        verify(vcService, times(1)).addvoter(any(VCList.class));
        
        LOGGER.debug("Result from service: {}", result);
    }
	
	@Test
    public void testGetVoter() {
		VCList voter1 = new VCList(101, "Sylvester", "Chennai", 26);
		VCList voter2 = new VCList(102, "Srikanth", "Hyderabad", 24);
        List<VCList> voters = Arrays.asList(voter1, voter2);
        when(vcService.getvoter()).thenReturn(voters);

        List<VCList> result = vccontroller.getvoter();

        assertNotNull(result, "The list of voters should not be null");
        assertEquals(voters, result);
        verify(vcService, times(1)).getvoter();
    }
    

}
