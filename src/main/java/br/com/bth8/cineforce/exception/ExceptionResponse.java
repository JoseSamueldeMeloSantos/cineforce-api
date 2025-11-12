package br.com.bth8.cineforce.exception;

import java.time.LocalDate;

public record ExceptionResponse(
        LocalDate timestamp,
        String message,
        String details
) {
}
