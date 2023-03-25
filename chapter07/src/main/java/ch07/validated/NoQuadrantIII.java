package ch07.validated;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidatedQuadrantIII.class})
@Documented
public @interface NoQuadrantIII {
    String message() default "Failed quadrant III test";

    Class<?>[] groups() default{};


    Class<? extends Payload>[] payload() default {};
}
