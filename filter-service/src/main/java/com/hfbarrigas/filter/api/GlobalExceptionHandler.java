package com.hfbarrigas.filter.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hfbarrigas.filter.exceptions.InvalidRequestException;
import com.hfbarrigas.filter.logger.Loggable;
import com.hfbarrigas.filter.model.api.ErrorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
@Component
public class GlobalExceptionHandler implements Loggable {

    private ObjectMapper objectMapper;

    @Autowired
    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorDetails> handle(MethodArgumentNotValidException e, ServerHttpRequest request) {
        logger().error("MethodArgumentNotValidException:", e);
        return Mono.just(ErrorDetails.builder()
                .withTimestamp(OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()))
                .withException(e.getClass().getSimpleName())
                .withMessage(Arrays.toString(e.getBindingResult().getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList())
                        .toArray()))
                .withPath(request.getPath().value())
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withError(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorDetails> handle(ConstraintViolationException e, ServerHttpRequest request) {
        logger().error("ConstraintViolationException", e);
        return Mono.just(ErrorDetails.builder()
                .withTimestamp(OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()))
                .withException(e.getClass().getSimpleName())
                .withMessage(Arrays.toString(e.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.toList())
                        .toArray()))
                .withPath(request.getPath().value())
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withError(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build());
    }


    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorDetails> handle(InvalidRequestException e, ServerHttpRequest request) {
        logger().error("InvalidRequestException", e);
        return Mono.just(ErrorDetails.builder()
                .withTimestamp(OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()))
                .withException(e.getClass().getSimpleName())
                .withMessage(e.getMessage())
                .withPath(request.getPath().value())
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withError(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorDetails> handle(Exception e, ServerHttpRequest request) {
        logger().error("Unexpected error", e);
        return Mono.just(ErrorDetails.builder()
                .withTimestamp(OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()))
                .withException(e.getClass().getSimpleName())
                .withMessage("Unexpected server error.")
                .withPath(request.getPath().value())
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build());
    }
}
