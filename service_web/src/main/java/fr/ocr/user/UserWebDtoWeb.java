package fr.ocr.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserWebDtoWeb implements Serializable , UserDetails {
    static final long serialVersionUID = 5453481303625368221L;

    private Integer idUser;

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    private String password;
    private String email;

    private HttpResponse<String> response;

    public HttpResponse<String> getResponse() {
        return response;
    }

    public void setResponse(HttpResponse<String> response) {
        this.response = response;
    }

    public UserWebDtoWeb(Integer idUser, String userName, String password, String email) {
        this.idUser = idUser;
        this.username = userName;
        this.password = password;
        this.email = email;
    }

    public UserWebDtoWeb() { }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public ResponseEntity<Map<String, Object>> formeReponseEntity(HttpResponse<String> httpResponse, UserWebDtoWeb userWebDtoWeb) {

        Map<String,Object> stringObjectMap = getStringStringMap(userWebDtoWeb);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(HttpStatus.valueOf(httpResponse.statusCode()));
        bodyBuilder.location(location).header("Content-Type", "application/json");

        return bodyBuilder.body(stringObjectMap);

    }

    public ResponseEntity<Map<String, Object>> formeReponseEntity(HttpStatus httpStatus, UserWebDtoWeb userWebDtoWeb) {

        Map<String,Object> stringObjectMap = getStringStringMap(userWebDtoWeb);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(httpStatus);
        bodyBuilder.location(location).header("Content-Type", "application/json");

        return bodyBuilder.body(stringObjectMap);

    }



    public Map<String, Object> getStringStringMap(UserWebDtoWeb userWebDtoWeb) {
        Map<String,Object> stringObjectMap = new HashMap<>();

        stringObjectMap.put("idUser", userWebDtoWeb.getIdUser());
        stringObjectMap.put("userName", userWebDtoWeb.getUsername());
        stringObjectMap.put("email", userWebDtoWeb.getEmail());

        return stringObjectMap;
    }

}
