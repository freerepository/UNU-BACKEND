package com.akashkumar.unu.ExceptionHandling;

import com.akashkumar.unu.ExceptionHandling.Address.AddressNotFound;
import com.akashkumar.unu.ExceptionHandling.AuthenticationException.UserAlreadyExistException;
import com.akashkumar.unu.ExceptionHandling.AuthenticationException.UserFound;
import com.akashkumar.unu.ExceptionHandling.AuthenticationException.UserNotFound;
import com.akashkumar.unu.ExceptionHandling.CategoryException.CategoryNotFound;
import com.akashkumar.unu.ExceptionHandling.Dto.ExceptionResponseDto;
import com.akashkumar.unu.ExceptionHandling.Products.OrderNotFound;
import com.akashkumar.unu.ExceptionHandling.Products.ProductNotFound;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleAlreadyUserExistsException(UserAlreadyExistException ex, WebRequest webRequest){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                webRequest.getDescription(false),
                HttpStatus.CONFLICT,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponseDto);
    }
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFound ex, WebRequest webRequest){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(webRequest.getDescription(false), HttpStatus.NOT_FOUND, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDto);
    }

    @ExceptionHandler(UserFound.class)
    public ResponseEntity<?> handleUserFounding(UserFound ex, WebRequest webRequest){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(webRequest.getDescription(false), HttpStatus.OK, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(exceptionResponseDto);
    }

    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<?> handleProductNotFound(ProductNotFound ex , WebRequest webRequest){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(webRequest.getDescription(false), HttpStatus.NOT_FOUND, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDto);
    }

    @ExceptionHandler(CategoryNotFound.class)
    public ResponseEntity<?> categoryNotFound(CategoryNotFound exception, WebRequest webRequest){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(webRequest.getDescription(false), HttpStatus.NOT_FOUND, exception.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDto);
    }

    @ExceptionHandler(AddressNotFound.class)
    public ResponseEntity<?> addressNotFound(AddressNotFound exception , WebRequest webRequest){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(webRequest.getDescription(false), HttpStatus.NOT_FOUND, exception.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDto);
    }


    @ExceptionHandler(OrderNotFound.class)
    public ResponseEntity<?> OrderNotFound(OrderNotFound exception, WebRequest webRequest){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(webRequest.getDescription(false), HttpStatus.NOT_FOUND, exception.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDto);
    }

/**************************************************************************************************************/
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest webRequest){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponseDto);
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<?> handleUnknownProperty(UnrecognizedPropertyException ex, WebRequest webRequest) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(webRequest.getDescription(false), HttpStatus.BAD_REQUEST, ex.getPropertyName(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDto);
    }
}
