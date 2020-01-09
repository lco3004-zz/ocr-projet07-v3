package fr.ocr;

import fr.ocr.exception.PrjExceptionHandler;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/*
 * http://localhost:9090/swagger-ui.html
 */
@SpringBootApplication
@PropertySources( {
        @PropertySource(value = "classpath:application.properties"),
        @PropertySource(value = "classpath:application.yml")})
@EnableTransactionManagement
@EnableSwagger2
public class ServiceCrudApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServiceCrudApplication.class, args);
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("fr.ocr.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public PrjExceptionHandler libHttpClient_Exception() {
         return PrjExceptionHandler.getInstance();
    }

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> 2018;
    }
}
