package fr.ocr.user;

import fr.ocr.exception.PrjExceptionHandler;
import io.swagger.annotations.Api;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
