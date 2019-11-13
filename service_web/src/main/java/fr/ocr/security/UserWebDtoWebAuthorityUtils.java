package fr.ocr.security;

import fr.ocr.user.UserWebDtoWeb;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.List;


public final class UserWebDtoWebAuthorityUtils {
    private static final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");

    public static Collection<? extends GrantedAuthority> createAuthorities(UserWebDtoWeb userWebDtoWeb) {
        String username = userWebDtoWeb.getUsername();
        return USER_ROLES;
    }

    private UserWebDtoWebAuthorityUtils() {
    }

}
