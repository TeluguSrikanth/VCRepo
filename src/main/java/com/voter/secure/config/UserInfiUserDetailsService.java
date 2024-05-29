package com.voter.secure.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.voter.secure.dto.UserInfi;
import com.voter.secure.repo.UserInfiRepository;

@Component
public class UserInfiUserDetailsService implements UserDetailsService {

	@Autowired
	private UserInfiRepository userInfiRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfi> userInfi = userInfiRepository.findByName(username);
        return userInfi.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

	}

}
