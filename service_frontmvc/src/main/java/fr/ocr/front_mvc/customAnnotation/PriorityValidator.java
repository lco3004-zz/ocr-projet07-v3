package fr.ocr.front_mvc.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PriorityValidator implements ConstraintValidator <PriorityCtrl,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.trim().length() ==1 && "LMH".contains(value.toUpperCase());
    }
}
