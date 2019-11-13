package fr.ocr.user.security;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.exception.PrjExceptionHandler;
import fr.ocr.user.UserWebDtoWeb;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
public class UserWebServiceImpl implements UserWebService {

	private final RestClient restClient;
	private final ObjectMapper objectMapper;
	private final PrjExceptionHandler prjExceptionHandler;

	private UserWebDtoWeb userWebDtoWeb;

	public UserWebServiceImpl(RestClient restClient, ObjectMapper objectMapper, PrjExceptionHandler prjExceptionHandler) {
		this.restClient = restClient;
		this.objectMapper = objectMapper;
		this.prjExceptionHandler = prjExceptionHandler;
		this.userWebDtoWeb = null;
	}

	public UserWebDtoWeb getUserWebDtoWeb() {
		return userWebDtoWeb;
	}

	public void setUserWebDtoWeb(UserWebDtoWeb userWebDtoWeb) {
		this.userWebDtoWeb = userWebDtoWeb;
	}

	@Override
	public UserDetails doesUserExist(Authentication authentication) throws UsernameNotFoundException{
		try {
			userWebDtoWeb = getFromServiceCrud(authentication.getName());
		} catch (IOException | InterruptedException e) {
			throw new UsernameNotFoundException("Nom inconnu");
		}
		return userWebDtoWeb;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			userWebDtoWeb = getFromServiceCrud(username);
		} catch (IOException | InterruptedException e) {
			throw new UsernameNotFoundException("Nom inconnu");
		}
		userWebDtoWeb.setAuthorities(AuthorityUtils.createAuthorityList("ROLE_USER"));
		return userWebDtoWeb;
	}

	public UserWebDtoWeb getFromServiceCrud(String nomUser) throws IOException, InterruptedException{
		String uriUserByName = "http://localhost:9090/UserByName/"+ nomUser;

		HttpRequest request = restClient.requestBuilder(URI.create(uriUserByName), null).GET().build();

		HttpResponse<String> response = restClient.send(request);

		if (response.statusCode() != HttpStatus.OK.value()) {
			prjExceptionHandler.throwUserUnAuthorized();
		} else {
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			userWebDtoWeb = objectMapper.readValue(response.body(), UserWebDtoWeb.class);
			userWebDtoWeb.setResponse(response);
		}
		return userWebDtoWeb;
	}
}
