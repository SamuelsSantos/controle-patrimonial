package br.com.navita.controlepatrimonial.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Custom response entity exception handle.
 */
@RestControllerAdvice
public class CustomResponseEntityExceptionHandle extends ResponseEntityExceptionHandler {

    /**
     * Handle constraint violation response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler({ConstraintViolationException.class})
    ResponseEntity<?> handleConstraintViolation(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        Set<String> messages = new HashSet<>(constraintViolations.size());
        messages.addAll(constraintViolations.stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .collect(Collectors.toList()));

        return ResponseEntity
                .badRequest()
                .body(messages);
    }

}
