package br.com.bth8.cineforce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EnitityNotFoundException extends RuntimeException{
    public EnitityNotFoundException(String message) {
        super(message);
    }
    public EnitityNotFoundException() {super("Enitity Not Found Exception");}
}

