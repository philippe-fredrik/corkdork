package se.iths.corkdork.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueUserValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUser {
    String message() default "Username is already registered";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
