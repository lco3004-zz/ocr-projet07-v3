package fr.ocr.userdetails;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.exception.PrjExceptionHandler;
import fr.ocr.model.UserWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;

@Component
public class UserWebUserDetailsService implements UserDetailsService {
    UserWeb userWeb;
    private final RestClient restClient;
    private final PrjExceptionHandler prjExceptionHandler;
    private final ObjectMapper objectMapper;


    @Autowired
    public UserWebUserDetailsService(RestClient restClient, PrjExceptionHandler prjExceptionHandler, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.prjExceptionHandler = prjExceptionHandler;
        this.objectMapper = objectMapper;
    }

    @Override
    public UserWebUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserWebUserDetails userWebUserDetails=null;
        try {
            userWebUserDetails = getFromServiceCrud(username);
        } catch (IOException | InterruptedException e) {
            throw new UsernameNotFoundException("Nom inconnu");
        }
        return userWebUserDetails;
    }

    public UserWebUserDetails doesUserExist(Authentication authentication) throws UsernameNotFoundException{
        UserWebUserDetails userWebUserDetails=null;
        try {
            userWebUserDetails = getFromServiceCrud(authentication.getName());
        } catch (IOException | InterruptedException e) {
            throw new UsernameNotFoundException("Nom inconnu");
        }
        return userWebUserDetails;
    }


    public UserWebUserDetails getFromServiceCrud(String nomUser) throws IOException, InterruptedException{
        String uriUserByName = "http://localhost:9090/UserByName/"+ nomUser;

        HttpRequest request = restClient.requestBuilder(URI.create(uriUserByName), null).GET().build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() != HttpStatus.OK.value()) {
            prjExceptionHandler.throwUserUnAuthorized();
        } else {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            userWeb = objectMapper.readValue(response.body(), UserWeb.class);
        }
        Collection<GrantedAuthority> authorities= AuthorityUtils.createAuthorityList("ROLE_USER");
        UserWebUserDetails userWebUserDetails = new UserWebUserDetails(userWeb, authorities);
        userWebUserDetails.setResponse(response);
        return userWebUserDetails;
    }

}
