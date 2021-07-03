package com.leosbelpoll.mancala.dto.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RangeValidator.class)
@Target(value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RangeConstraint {
    String message() default "Error";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String max() default "";
}
