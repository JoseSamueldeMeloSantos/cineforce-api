package br.com.bth8.cineforce.controller;

import br.com.bth8.cineforce.model.dto.request.CreateReferenceRequestDTO;
import br.com.bth8.cineforce.model.dto.response.CreatePreferenceResponseDTO;
import br.com.bth8.cineforce.service.CreatePaymentPreferenceService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@Slf4j
public class CreatePaymentPreferenceController {

    @Autowired
    private CreatePaymentPreferenceService service;

    @PostMapping
    public ResponseEntity<CreatePreferenceResponseDTO> createPreference(
            @Valid @RequestBody CreateReferenceRequestDTO request
            ) {

        try {
            CreatePreferenceResponseDTO useCaseOutput = service.createPreference(request);

            CreatePreferenceResponseDTO responseDTO = new CreatePreferenceResponseDTO(
                    useCaseOutput.preferenceId(),
                    useCaseOutput.redirectUrl()
            );
            return ResponseEntity.ok(responseDTO);

        } catch (IllegalArgumentException e) {
            log.warn("Invalid request data for creating preference: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null); // Or return an error response DTO
        } catch (Exception e) {
            log.error("Error creating payment preference for userId {}: {}", request.userId(), e.getMessage(), e);
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
