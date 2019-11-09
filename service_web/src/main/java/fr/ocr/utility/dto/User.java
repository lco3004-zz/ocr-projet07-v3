package fr.ocr.utility.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;


public class User implements Serializable , UserDetails{
    static final long serialVersionUID = 5453481303625368221L;

    private Integer idusager;
    private String userName;
    private String password;
    private String courriel;

    public User(Integer idusager, String userName, String password, String courriel) {
        this.idusager = idusager;
        this.userName = userName;
        this.password = password;
        this.courriel = courriel;
    }

    public User() {

    }

    public Integer getIdusager() {
        return idusager;
    }

    public void setIdusager(Integer idusager) {
        this.idusager = idusager;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
