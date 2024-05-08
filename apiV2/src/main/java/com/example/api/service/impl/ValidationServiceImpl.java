package com.example.api.service.impl;


import com.example.api.service.ValidationService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final Validator validator;
    @Override
    public void validate(Object request) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);

        if (!constraintViolations.isEmpty()) {

            throw new ConstraintViolationException(constraintViolations);
        }
    }
}

