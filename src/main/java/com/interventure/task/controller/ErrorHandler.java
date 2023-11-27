package com.interventure.task.controller;

import com.interventure.task.dto.response.ErrorResponse;
import com.interventure.task.exception.ErrorCode;
import com.interventure.task.exception.InternalServiceException;
import com.interventure.task.exception.ProductServiceException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author bojana
 */
@ControllerAdvice
@Log4j2
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        log.error(exception.getMessage());


        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        
        String allErrors = errors.stream().map(Object::toString).collect(Collectors.joining(","));
        
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(allErrors)
                .errorCode(ErrorCode.BAD_REQUEST.getValue())
                .build(), HttpStatus.BAD_REQUEST);

    }
    
    

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<ErrorResponse> handleProductServiceException(ProductServiceException exception) {
        
        log.error(exception.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(exception.getMessage())
                .errorCode(exception.getErrorCode().getValue())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalServiceException.class)
    public ResponseEntity<ErrorResponse> handleProductServiceException(InternalServiceException exception) {
        log.error(exception.getStackTrace());
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(exception.getMessage())
                .errorCode(ErrorCode.INTERNAL_ERROR.getValue())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
