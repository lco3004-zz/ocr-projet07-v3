package fr.ocr.controller;

import fr.ocr.exception.PrjExceptionHandler;
import fr.ocr.model.UserWeb;
import fr.ocr.service.UserWebService;
import fr.ocr.utility.InfosConnexionUser;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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



    private final PrjExceptionHandler prjExceptionHandler;

    private  final UserWebService userWebService;

    @Autowired
    public UserWebController(PrjExceptionHandler prjExceptionHandler, UserWebService userWebService ){
        this.prjExceptionHandler = prjExceptionHandler;
        this.userWebService = userWebService;
    }

    @GetMapping(value="/tokenInfos")
    public Map<String, String> tokenInfos(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }

    @RequestMapping("/userInfos")
    public Principal userInfos(Principal user) {
        return user;
    }


    @PostMapping(value = "/loginUser")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserWeb user, HttpServletResponse response) {

        UserDetails userDetails;

        if ((userDetails= userWebService.attemptAuthentication(user)) == null)
            prjExceptionHandler.throwUserUnAuthorized();

        return InfosConnexionUser.formeReponseEntity(response,  userDetails);
    }

    @GetMapping(value = "/logoutUser")
    public ResponseEntity<String> logoutUser(HttpServletRequest request, HttpServletResponse response) {

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
