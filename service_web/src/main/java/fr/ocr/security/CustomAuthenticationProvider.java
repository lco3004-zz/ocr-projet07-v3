package fr.ocr.security;

import fr.ocr.user.UserService;
import fr.ocr.user.UserWebDtoWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	public CustomAuthenticationProvider() {
        super();
    }
	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final String username = authentication.getName();
		final String password = authentication.getCredentials().toString();

		UserWebDtoWeb userWebDtoWeb = null;
		try {
			userWebDtoWeb = userService.doesUserExist(authentication);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

		if (userWebDtoWeb == null || ! userWebDtoWeb.getUserName().equalsIgnoreCase(username)) {
			throw new BadCredentialsException("Username not found.");
		}

		if (!password.equals(userWebDtoWeb.getPassword())) {
			throw new BadCredentialsException("Wrong password.");
		}

		List<GrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        final UserDetails principal = new User(username, password, authorities);

		return new UsernamePasswordAuthenticationToken(principal, password, authorities);
	}
	
	@Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
          UsernamePasswordAuthenticationToken.class);
    }

}