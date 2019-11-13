package fr.ocr.user.security;


import fr.ocr.user.UserWebDtoWeb;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;

public interface UserWebService extends UserDetailsService {

	UserDetails doesUserExist(Authentication authentication) throws UsernameNotFoundException;
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	UserWebDtoWeb getFromServiceCrud(String nomUser) throws IOException, InterruptedException;
}
