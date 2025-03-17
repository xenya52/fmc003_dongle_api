package com.xenya52.fmc003_rest_api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * GlobalExceptionHandler is a centralized exception handling class
 * that handles exceptions thrown by the application.
 *
 * It uses Spring's @ControllerAdvice to handle exceptions globally
 * and provides appropriate HTTP responses for different types of exceptions.
 *
 * The class includes handlers for:
 * - IllegalArgumentException: Returns a BAD_REQUEST status.
 * - OptimisticLockingFailureException: Returns a CONFLICT status.
 * - Generic exceptions: Returns an INTERNAL_SERVER_ERROR status.
 *
 * Each handler logs the exception using SLF4J.
 */@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(
        GlobalExceptionHandler.class
    );

    /**
     * Handles IllegalArgumentException.
     *
     * @param ex the IllegalArgumentException
     * @return a ResponseEntity with the error message and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(
        IllegalArgumentException ex
    ) {
        logger.error("IllegalArgumentException: ", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles OptimisticLockingFailureException.
     *
     * @param ex the OptimisticLockingFailureException
     * @return a ResponseEntity with the error message and HTTP status CONFLICT
     */
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<String> handleOptimisticLockingFailureException(
        OptimisticLockingFailureException ex
    ) {
        logger.error("OptimisticLockingFailureException: ", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Handles generic exceptions.
     *
     * @param ex the Exception
     * @return a ResponseEntity with the error message and HTTP status INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("Exception: ", ex);
        return new ResponseEntity<>(
            ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
