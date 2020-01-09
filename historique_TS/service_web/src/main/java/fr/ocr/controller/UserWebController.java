/*
* Controller
* * login usager
* * CORS : *
 */
package fr.ocr.controller;

import fr.ocr.exception.PrjExceptionHandler;
import fr.ocr.model.UserWeb;
import fr.ocr.service.UserWebService;
import fr.ocr.userdetails.UserWebUserDetails;
import fr.ocr.utility.InfosConnexionUser;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;


@Api(value = "APIs de gestion de users.")
@RestController
@RequestMapping("/gestionUsagers/*")
@CrossOrigin(origins= "*", methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST}, allowCredentials = "true")
public class UserWebController {


    private final PrjExceptionHandler prjExceptionHandler;

    private  final UserWebService userWebService;

    @Autowired
    public UserWebController(PrjExceptionHandler prjExceptionHandler, UserWebService userWebService ){
        this.prjExceptionHandler = prjExceptionHandler;
        this.userWebService = userWebService;
    }

    @PostMapping(value = "/loginUser", produces="application/json")
    public  ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserWeb user, HttpServletResponse response) {

        String idUserSession = "";

        if ((userWebService.attemptAuthentication(user)) == null)
            prjExceptionHandler.throwUserUnAuthorized();

        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        assert principal instanceof UserWebUserDetails;
        UserWebUserDetails userWebUserDetails = (UserWebUserDetails)principal;

        HttpServletRequest httpServletRequest = attr.getRequest();
        if (httpServletRequest != null) {
            HttpSession httpSession = httpServletRequest.getSession(true);
            if (httpSession != null) {
                idUserSession = httpSession.getId();
            }
        }

        return InfosConnexionUser.formeReponseEntity(response, userWebUserDetails,idUserSession);
    }

    @GetMapping(value = "/logoutUser", produces="application/json")
    public  ResponseEntity<String> logoutUser(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null ) {
             new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        else
            prjExceptionHandler.throwUserUnAuthorized();
        return  new ResponseEntity<>(HttpStatus.OK);
    }
}
