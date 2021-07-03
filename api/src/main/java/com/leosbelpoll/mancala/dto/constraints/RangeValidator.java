package com.leosbelpoll.mancala.dto.constraints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class RangeValidator implements ConstraintValidator<RangeConstraint, Integer> {

    @Autowired
    private Environment environment;

    private String property;

    @Override
    public void initialize(RangeConstraint rangeConstraint) {
        property = rangeConstraint.max();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        int max = Integer.parseInt(Objects.requireNonNull(environment.getProperty(property)));
        return value == null || (value > 0 && value <= max);
    }
}
