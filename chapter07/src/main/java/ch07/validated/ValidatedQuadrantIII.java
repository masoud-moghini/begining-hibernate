package ch07.validated;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class  ValidatedQuadrantIII implements ConstraintValidator<NoQuadrantIII,Coordinate> {
    @Override
    public void initialize(NoQuadrantIII constraintAnnotation) {
    }


    @Override
    public boolean isValid(Coordinate o, ConstraintValidatorContext constraintValidatorContext) {
        return !(o.getX() < 0 && o.getY() < 0);
    }
}
