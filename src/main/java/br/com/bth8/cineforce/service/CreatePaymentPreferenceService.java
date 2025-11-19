package br.com.bth8.cineforce.service;

import br.com.bth8.cineforce.infra.Client.MercadoPagoClient;
import br.com.bth8.cineforce.exception.PaymentGatewayException;
import br.com.bth8.cineforce.model.dto.request.CreateReferenceRequestDTO;
import br.com.bth8.cineforce.model.dto.response.CreatePreferenceResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CreatePaymentPreferenceService {

    private final MercadoPagoClient mercadoPagoClient;

    public CreatePreferenceResponseDTO createPreference(CreateReferenceRequestDTO request) {

        String orderNumber = "";
        try {
            CreatePreferenceResponseDTO response = mercadoPagoClient.createPreference(request, orderNumber);
            return response;
        } catch (PaymentGatewayException e) {
            log.error("Error creating payment preference via gateway for orderId {}: {}", orderNumber, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during payment preference creation for orderId {}: {}", orderNumber, e.getMessage(), e);
            throw new RuntimeException("An unexpected error occurred while creating the payment preference.", e);
        }
    }
}
