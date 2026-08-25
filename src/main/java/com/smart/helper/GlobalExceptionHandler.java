package com.smart.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.smart.dto.ErrorMessage;

import jakarta.servlet.http.HttpServletRequest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private boolean isProduction = false; // Set to true for production

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleAccessDeniedException(
            AccessDeniedException ex, HttpServletRequest request) {
        ErrorMessage errorResponse = new ErrorMessage(
                "Access Denied",
                ex.getMessage(),
                getStackTraceIfNeeded(ex),
                HttpStatus.FORBIDDEN
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorMessage> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpServletRequest request) {
        
        String requestedPath = request.getRequestURI(); // Get the requested URI
        String customErrorMessage = "API Not Found: " + requestedPath; // Set API-specific message

        ErrorMessage errorResponse = new ErrorMessage(
                customErrorMessage, // Set custom error
                customErrorMessage, // Message matches custom error
                null,               // Stack trace is optional
                HttpStatus.NOT_FOUND
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        ErrorMessage errorResponse = new ErrorMessage(
                "Validation Error",
                ex.getBindingResult().toString(),
                getStackTraceIfNeeded(ex),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGenericException(
            Exception ex, HttpServletRequest request) {

        ex.printStackTrace(); // Prints full stack trace in the console

        ErrorMessage errorResponse = new ErrorMessage(
                "An unexpected error occurred",
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getStackTraceIfNeeded(Exception ex) {
        if (!isProduction) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            return sw.toString();
        }
        return null;
    }
    
}
