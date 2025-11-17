package br.com.bth8.cineforce.controller;

import br.com.bth8.cineforce.model.dto.request.CreateReferenceRequestDTO;
import br.com.bth8.cineforce.model.dto.response.CreatePreferenceResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    public ResponseEntity<CreatePreferenceResponseDTO> createPreference(
            @Valid @RequestBody CreateReferenceRequestDTO request
            ) {

    }
}
