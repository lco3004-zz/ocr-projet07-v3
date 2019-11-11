package fr.ocr.user;


import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface UserService {
	

	UserWebDtoWeb doesUserExist(Authentication authentication) throws IOException, InterruptedException;
	
	UserWebDtoWeb getByEmail(String email);
	
	UserWebDtoWeb isValidUser(String email, String password) ;
}
