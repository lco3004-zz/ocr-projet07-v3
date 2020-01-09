/*
* Extension de UserDetail pour ajouter les infos usager propre à l'applixation
 */

package fr.ocr.front_mvc.userdetails;

import fr.ocr.front_mvc.model.UserWeb;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.net.http.HttpResponse;
import java.util.Collection;

//@Component
public class UserWebUserDetails  extends User implements UserDetails {

    public UserWeb getUserWeb() {
        return userWeb;
    }

    public void setUserWeb(UserWeb userWeb) {
        this.userWeb = userWeb;
    }

    private  UserWeb userWeb ;

    private HttpResponse<String> response;

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    private Collection<GrantedAuthority> authorities;

    public  UserWebUserDetails(UserWeb userWeb,  Collection<GrantedAuthority> authorities) {
        super(userWeb.getUsername(),userWeb.getPassword(),authorities);
        this.userWeb = userWeb;
        this.authorities = authorities;
        userWeb.setIdUser(userWeb.getIdUser());
        userWeb.setEmail(userWeb.getEmail());
        userWeb.setUsername(userWeb.getUsername());
        userWeb.setPassword(userWeb.getPassword());
    }

    public HttpResponse<String> getResponse() {
        return response;
    }

    public void setResponse(HttpResponse<String> response) {
        this.response = response;
    }


    @Override
    public Collection<GrantedAuthority> getAuthorities() {
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
