package fr.ocr.userdetails;

import fr.ocr.model.UserWeb;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;

//@Component
public class UserWebUserDetails  extends UserWeb implements UserDetails {

    private HttpResponse<String> response;

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    private List<GrantedAuthority> authorities;

    public  UserWebUserDetails(UserWeb userWeb) {
        setIdUser(userWeb.getIdUser());
        setEmail(userWeb.getEmail());
        setUsername(userWeb.getUsername());
        setPassword(userWeb.getPassword());
    }

    public HttpResponse<String> getResponse() {
        return response;
    }

    public void setResponse(HttpResponse<String> response) {
        this.response = response;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities; //AuthorityUtils.createAuthorityList("ROLE_USER");
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
}
