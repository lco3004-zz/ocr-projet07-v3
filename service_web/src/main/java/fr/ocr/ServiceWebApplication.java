package fr.ocr;

import fr.ocr.exception.PrjExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@PropertySource("classpath:application.yml")
@EnableSwagger2
public class ServiceWebApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServiceWebApplication.class, args);
    }
    @Bean
    public RestClient libHttpClient() {
        return new RestClient() ;
    }

    @Bean
    public PrjExceptionHandler libHttpClient_Exception() {
        return PrjExceptionHandler.getInstance();
    }
}
