package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dto.ErrorDto;
import com.davidmaisuradze.gymapplication.exception.GymException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(GymException.class)
    public ResponseEntity<ErrorDto> handleCreationException(GymException e) {
        ErrorDto dto = new ErrorDto();
        dto.setErrorCode(e.getErrorCode());
        dto.setErrorMessage(e.getMessage());
        HttpStatus status = codeToStatus(dto.getErrorCode());
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(dto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationError(MethodArgumentNotValidException e) {
        ErrorDto errorDto = new ErrorDto();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> errorDto.getDetails().put(fieldError.getField(), fieldError.getDefaultMessage()));

        errorDto.setErrorMessage("Validation error");
        errorDto.setErrorCode("500");
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private HttpStatus codeToStatus(String errorCode) {
        try {
            int status = Integer.parseInt(errorCode);
            return HttpStatus.resolve(status) != null ?
                    HttpStatus.resolve(status) :
                    HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
