package br.com.bth8.cineforce.controller;

import br.com.bth8.cineforce.model.dto.request.EmailRequestDTO;
import br.com.bth8.cineforce.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService service;

    @PostMapping
    public ResponseEntity<String> sendEmail(
            @RequestBody EmailRequestDTO emailRequestDTO
            ) {
        return new ResponseEntity<>("e-Mail sent with sucess", HttpStatus.OK);
    }
}
