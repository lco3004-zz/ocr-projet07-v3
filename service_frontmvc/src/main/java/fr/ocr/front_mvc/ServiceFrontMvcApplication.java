package fr.ocr.front_mvc;

import fr.ocr.RestClient;
import fr.ocr.exception.PrjExceptionHandler;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@PropertySources( {
        @PropertySource(value = "classpath:application.properties"),
        @PropertySource(value = "classpath:application.yml")})
@EnableSwagger2
public class ServiceFrontMvcApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServiceFrontMvcApplication.class, args);
    }
    @Bean
    public RestClient libHttpClient() {
        return new RestClient() ;
    }

    @Bean
    public PrjExceptionHandler libHttpClient_Exception() {
        return PrjExceptionHandler.getInstance();
    }

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> 2020;
    }
}
