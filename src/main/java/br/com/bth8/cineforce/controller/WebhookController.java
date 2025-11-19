package br.com.bth8.cineforce.controller;

import br.com.bth8.cineforce.model.dto.MercadoPagoConfigDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/webhook")
public class WebhookController {

    public ResponseEntity<Void> webhookController(MercadoPagoConfigDTO mercadoPagoConfigDTO) {
        String resourceId = mercadoPagoConfigDTO.getData().getId();
        String resourceType = mercadoPagoConfigDTO.getType();

        try {

        }
    }
}
