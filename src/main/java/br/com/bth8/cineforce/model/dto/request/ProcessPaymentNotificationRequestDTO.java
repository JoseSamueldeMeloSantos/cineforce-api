package br.com.bth8.cineforce.model.dto.request;


public record ProcessPaymentNotificationRequestDTO (
        String resourceType, // e.g., "payment"
        String resourceId    // e.g., the payment ID
) {}
