package fr.ocr.front_mvc.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

public class CustomErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }
    @GetMapping("/error")
    public ModelAndView handleError (HttpServletResponse response){
        int errorCode = response.getStatus();
        System.out.println("Error with code " +errorCode+" happened");
        return new ModelAndView("");
    }

}
