package br.com.bth8.cineforce.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProccessPaymentNotificationResponseDTO {

    boolean success;
    String updatedStatus;
}
