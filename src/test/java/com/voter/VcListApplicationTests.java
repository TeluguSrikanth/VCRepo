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
import org.springframework.boot.test.context.SpringBootTest;

import com.voter.controller.VCcontroller;
import com.voter.entity.VCList;
import com.voter.service.VCService;

@SpringBootTest
class VcListApplicationTests {

	@InjectMocks
	private VCcontroller vccontroller;
	
	@Mock
	private VCService vcService;
	
	@Test
    public void testAddVoter() {
        VCList voter = new VCList(1, "Srikanth", "Hyderabad", 24);
        when(vcService.addvoter(any(VCList.class))).thenReturn(voter);

        VCList result = vccontroller.addvoter(voter);

        assertNotNull(result, "The saved voter should not be null");
        assertEquals(voter, result);
        verify(vcService, times(1)).addvoter(any(VCList.class));
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
