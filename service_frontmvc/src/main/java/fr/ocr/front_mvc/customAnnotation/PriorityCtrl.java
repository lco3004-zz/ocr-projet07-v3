package fr.ocr.front_mvc.customAnnotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PriorityValidator.class)
public @interface PriorityCtrl {
    String message() default  " Choix L M H" ;
    Class<?>[] groups() default {};
    Class < ? extends Payload> [] payload() default {};

}
