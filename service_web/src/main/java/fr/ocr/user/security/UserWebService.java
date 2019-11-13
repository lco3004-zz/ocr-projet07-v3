package fr.ocr.user.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserWebService extends UserDetailsService {

	UserDetails doesUserExist(Authentication authentication) throws UsernameNotFoundException;
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
