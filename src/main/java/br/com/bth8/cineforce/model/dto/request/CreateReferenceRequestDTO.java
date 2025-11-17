package br.com.bth8.cineforce.model.dto.request;

import br.com.bth8.cineforce.model.dto.CartDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateReferenceRequestDTO (

   UUID  userId,

   @DecimalMin(value = "0.01", message = "Total amount must be positive")
   BigDecimal totalAmount,

   @NotEmpty(message = "Items list cannot be empty")
   @Valid
   List<ItemDTO> items,

   @NotNull(message = "Payer information cannot be null")
   @Valid
   PayerDTO payer,

   @NotNull( message = "Payer information cannot be null")
   @Valid
   BackUrlsDTO backUrls,

   String notificationURrl
    ) {
    public record ItemDTO(
            @NotBlank(message = "Item ID cannot be blank")
            String id,
            @NotBlank(message = "Item title cannot be blank")
            String title,
            @NotNull(message = "Item quantity cannot be null")
            Integer quantity,
            @NotNull(message = "Item unit price cannot be null")
            @DecimalMin(value = "0.01", message = "Item unit price must be positive")
            BigDecimal unitPrice
    ) {}

    public record PayerDTO(
            @NotBlank(message = "Payer email cannot be blank")
            @Email(message = "Payer email must be valid")
            String email,
            @NotBlank(message = "Payer name cannot be blank")
            String name
    ) {}

    public  record  BackUrlsDTO (
            @NotBlank(message = "Succes URL cannot be blank")
            String succes,
            @NotBlank(message = "Failure URL cannot be blank")
            String failure,
            @NotBlank(message = "Pending URL cannot be blank")
            String pending
    ) {}
}
