package com.flight.management.ticket.infrastructure;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler
  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(
            ResourceNotFoundException ex, WebRequest request) {
        Map<String,String> bodyOfResponse = new LinkedHashMap<>();
        bodyOfResponse.put("message",ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value
      = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handle(
      RuntimeException ex, WebRequest request) {
        Map<String,String> bodyOfResponse = new LinkedHashMap<>();
        bodyOfResponse.put("message","Invalid request");

        ex.printStackTrace();

        return handleExceptionInternal(ex, bodyOfResponse, 
          new HttpHeaders(), HttpStatus.CONFLICT, request);
    }


    @ExceptionHandler(value
            = { Exception.class })
    protected ResponseEntity<Object> handleAny(
            RuntimeException ex, WebRequest request) {
        Map<String,String> bodyOfResponse = new LinkedHashMap<>();
        bodyOfResponse.put("message","An unexpected error occurred, please try again later.");
        ex.printStackTrace();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}