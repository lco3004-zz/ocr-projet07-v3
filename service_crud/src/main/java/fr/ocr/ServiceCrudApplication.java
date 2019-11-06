package fr.ocr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/*
 * http://localhost:9090/swagger-ui.html
 */
@SpringBootApplication
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableSwagger2
public class ServiceCrudApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServiceCrudApplication.class, args);
    }

}
