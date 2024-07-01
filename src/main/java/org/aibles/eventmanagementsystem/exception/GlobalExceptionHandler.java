package org.aibles.eventmanagementsystem.exception;

import lombok.extern.slf4j.Slf4j;
import org.aibles.eventmanagementsystem.constant.CacheConstant;
import org.aibles.eventmanagementsystem.dto.response.ActiveAccountResponse;
import org.aibles.eventmanagementsystem.exception.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception) {
        return handleException(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException exception) {
        return handleException(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException exception) {
        return handleException(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException exception) {
        return handleException(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exception) {
        return handleException(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalErrorException(InternalErrorException exception) {
        return handleException(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse(
                CacheConstant.BAD_REQUEST,
                Instant.now().getEpochSecond(),
                errors
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> handleException(BaseException exception, HttpStatus code) {
        Map<String, String> error = new HashMap<>();
        error.put("message", exception.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                code.getReasonPhrase().toLowerCase().replace(" ", "_"),
                Instant.now().getEpochSecond(),
                error
        );
        return new ResponseEntity<>(errorResponse, code);
    }

    @ExceptionHandler(AccountAlreadyActivatedException.class)
    public ResponseEntity<ActiveAccountResponse> handleAccountAlreadyActivatedException(AccountAlreadyActivatedException ex) {
        log.error("Account already activated: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ActiveAccountResponse("account_activated", System.currentTimeMillis(), null, null)
        );
    }
}
