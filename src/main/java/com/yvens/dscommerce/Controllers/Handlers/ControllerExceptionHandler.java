package com.yvens.dscommerce.Controllers.Handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.yvens.dscommerce.DTO.CustomErros;
import com.yvens.dscommerce.Services.Exceptions.DatabaseException;
import com.yvens.dscommerce.Services.Exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;



@ControllerAdvice
public class ControllerExceptionHandler {
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<CustomErros> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request)
 {
HttpStatus status = HttpStatus.NOT_FOUND;
CustomErros err = new CustomErros(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
return ResponseEntity.status(status).body(err);
}

@ExceptionHandler(DatabaseException.class)
public ResponseEntity<CustomErros> Database(DatabaseException e, HttpServletRequest request)
 {
HttpStatus status = HttpStatus.BAD_REQUEST;
CustomErros err = new CustomErros(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
return ResponseEntity.status(status).body(err);
}

}
