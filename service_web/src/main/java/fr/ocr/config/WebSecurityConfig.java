package fr.ocr.config;

import fr.ocr.security.CustomAuthenticationProvider;
import fr.ocr.user.UserWebService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private  final UserWebService userDetailsService;

      private final CustomAuthenticationProvider authProvider;

    public WebSecurityConfig(UserWebService userDetailsService, CustomAuthenticationProvider authProvider) {
        this.authProvider = authProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authBuilder) {
        authBuilder.authenticationProvider(this.authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .authenticationProvider(authProvider)
                .formLogin()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint() {})
                .and()
                .authorizeRequests()
                .antMatchers("/account/loginUser").permitAll()
                .antMatchers("/account/logoutUser").authenticated()
                .antMatchers("/account/userInfos").authenticated()
                .antMatchers("/account/tokenInfos").authenticated()
                .antMatchers("/listeOuvrages").authenticated()
                .antMatchers("/prolongerPret").authenticated();
                //.anyRequest().permitAll();

    }

    private class AuthentificationLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                    Authentication authentication) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    private class AuthentificationLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response, Authentication authentication)
                throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
