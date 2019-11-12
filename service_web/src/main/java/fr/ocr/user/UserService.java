package fr.ocr.user;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

public interface UserService extends UserDetailsService {

	UserWebDtoWeb doesUserExist(Authentication authentication) throws IOException, InterruptedException;
}
