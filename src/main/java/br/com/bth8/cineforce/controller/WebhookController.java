package br.com.bth8.cineforce.controller;


import br.com.bth8.cineforce.model.dto.MercadoPagoNotificationDTO;
import br.com.bth8.cineforce.model.dto.request.ProcessPaymentNotificationRequestDTO;
import br.com.bth8.cineforce.service.ProcessPaymentNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final ProcessPaymentNotificationService service;


    @PostMapping("/mercadopago")
    public ResponseEntity<Void> handleMercadoPagoNotification(
            @RequestBody MercadoPagoNotificationDTO notification) {

        if (notification == null || notification.getData() == null || notification.getData().getId() == null || notification.getType() == null) {
            log.warn("Received invalid Mercado Pago notification");
            return ResponseEntity.ok().build();
        }

        String resourceId = notification.getData().getId(); // payment id
        String resourceType = notification.getType(); // e.g., "payment"

        if (!"payment".equalsIgnoreCase(resourceType)) {
            log.info("Ignoring non-payment notification type: {}", resourceType);
            return ResponseEntity.ok().build();
        }

        try {
            var request = new ProcessPaymentNotificationRequestDTO(resourceType, resourceId);
            var result = service.processPaymentNotification(request);

            if (result.isSuccess()) {
                log.info("Payment processed successfully");
            } else {
                log.warn("Payment  processing returned failure.");
            }

            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            log.error("Error processing payment notification");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
