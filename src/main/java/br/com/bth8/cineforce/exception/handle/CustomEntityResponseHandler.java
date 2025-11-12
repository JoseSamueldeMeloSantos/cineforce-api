package br.com.bth8.cineforce.exception.handle;

import br.com.bth8.cineforce.exception.EnitityNotFoundException;
import br.com.bth8.cineforce.exception.EntityAlreadyExistsException;
import br.com.bth8.cineforce.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@ControllerAdvice
@RestController
public class CustomEntityResponseHandler {

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public  final ResponseEntity<ExceptionResponse> handleEnityAlreadyExistsException(
            Exception ex,
            WebRequest request
    ) {
        ExceptionResponse response = new ExceptionResponse(
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EnitityNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleEnitityNotFoundException(
            Exception ex,
            WebRequest request
    ) {
        ExceptionResponse response = new ExceptionResponse(
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleException(
            Exception ex,
            WebRequest request
    ) {
        ExceptionResponse response = new ExceptionResponse(
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
