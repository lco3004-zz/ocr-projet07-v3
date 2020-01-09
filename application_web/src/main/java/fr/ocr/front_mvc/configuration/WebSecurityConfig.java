/*
 * Configuration
 * les URI sont accesibles si usager est authentifié sinon refus
 * l'URI de login est accessible à anonymous
 * LA MAQUETTE est en DEBUG Spring SECURITY !!
 * CSRF est invalidé
 * CORS est présent (mais c'est par déf dans les navigateurs actuels)
 * max session par user == 1 (ne semble pas fonctionné dans ce code)
 */

package fr.ocr.front_mvc.configuration;


import fr.ocr.front_mvc.authentication.CustomAuthenticationProvider;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;


@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationProvider authProvider;

    public WebSecurityConfig(CustomAuthenticationProvider authProvider) {
        this.authProvider = authProvider;
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

        http.cors().and().csrf().disable();
        http.sessionManagement().maximumSessions(1);
        http.authenticationProvider(authProvider).formLogin().disable();
        http.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint() {});

        http.authorizeRequests()
                .antMatchers("/loginUser").permitAll()
                .antMatchers("/listeOuvrages").permitAll()
                .antMatchers("/rechercherOuvrages").permitAll()
                .antMatchers("/gestionPrets/prolongerPret").authenticated()
                .antMatchers("/gestionPrets/listePrets").authenticated();
    }

}
