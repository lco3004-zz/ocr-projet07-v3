package fr.ocr.user;

import fr.ocr.exception.PrjExceptionHandler;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;


@Api(value = "APIs de gestion de users.")
@RestController
@RequestMapping("/account/*")
public class UserWebController {

    private final AuthenticationProvider authenticationProvider;

    private final PrjExceptionHandler prjExceptionHandler;


    public UserWebController(AuthenticationProvider authenticationProvider, PrjExceptionHandler prjExceptionHandler) {
        this.authenticationProvider = authenticationProvider;
        this.prjExceptionHandler = prjExceptionHandler;
    }
    @GetMapping(value="/token")
    public Map<String, String> getToken(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }


    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, Object>> ConnexionUser(@RequestBody UserWebDtoWeb user, HttpServletResponse response) {
        Authentication authentication = null;
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        UserDetails userDetails ;
        try {
            authentication = this.authenticationProvider.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
             userDetails = (UserDetails) authentication.getPrincipal();

        } catch (Exception e) {
            prjExceptionHandler.throwUserUnAuthorized();
        }
        user.setEmail(userDetails.);
        return user.formeReponseEntity(HttpStatus.valueOf(response.getStatus()) , user);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<String> DeconnexionUser(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails user = null;
        if (authentication != null ) {
             user = (UserDetails) authentication.getPrincipal();
             new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        else
            prjExceptionHandler.throwUserUnAuthorized();
        return  new ResponseEntity<String>(HttpStatus.OK);
    }
}
