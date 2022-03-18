package com.luisdias.rmmservice.modules.shared.api;

import com.luisdias.rmmservice.modules.shared.dto.ErrorMessage;
import com.luisdias.rmmservice.modules.shared.exception.EntityNotFoundException;
import com.luisdias.rmmservice.modules.shared.exception.UnauthorizedException;
import com.luisdias.rmmservice.modules.shared.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ControllerAdvice
public class ExceptionHandlerApi {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public List<ErrorMessage> handleUnauthorizedException(UnauthorizedException ex) {
        return getMessageFromException(ex);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public List<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException ex) {
        return getMessageFromException(ex);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ValidationException.class)
    public List<ErrorMessage> handleValidationException(ValidationException ex) {
        return getMessageFromException(ex);
    }

    private List<ErrorMessage> getMessageFromException(RuntimeException ex) {
        return List.of(ErrorMessage.of(ex.getMessage()));
    }
}
