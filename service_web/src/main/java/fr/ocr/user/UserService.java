package fr.ocr.user;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;

public interface UserService extends UserDetailsService {

	UserWebDtoWeb doesUserExist(Authentication authentication) throws IOException, InterruptedException;
	UserWebDtoWeb loadUserByUsername(String username) throws UsernameNotFoundException;
}
