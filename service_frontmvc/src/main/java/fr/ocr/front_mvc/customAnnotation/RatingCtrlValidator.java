package fr.ocr.front_mvc.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RatingCtrlValidator implements ConstraintValidator <RatingCtrl, String >{
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Double rateVal ;
        try {
             rateVal = Double.parseDouble(value);

        }catch (NumberFormatException e) {
            return  false;
        }
        return (rateVal <=10.0 || rateVal >= 1.0);
    }
}
