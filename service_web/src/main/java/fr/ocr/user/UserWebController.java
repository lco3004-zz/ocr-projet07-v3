package fr.ocr.user;

import fr.ocr.exception.PrjExceptionHandler;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;


@Api(value = "APIs de gestion de users.")
@RestController
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
    public ResponseEntity<Map<String, Object>> ConnexionUser(@RequestBody UserWebDtoWeb user) {
        Authentication authentication = null;
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        try {
            authentication = this.authenticationProvider.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            user = (UserWebDtoWeb) authentication.getPrincipal();
            user.setPassword(null);

        } catch (Exception e) {
            prjExceptionHandler.throwUserUnAuthorized();
        }
        return user.formeReponseEntity(user.getResponse(), user);
    }
}
