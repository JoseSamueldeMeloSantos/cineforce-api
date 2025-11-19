package br.com.bth8.cineforce.controller;

import br.com.bth8.cineforce.service.GoogleDriveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController // Marca como controller REST
@RequestMapping("/drive") // Prefixo dos endpoints
@RequiredArgsConstructor
public class GoogleDriveController {

    private final GoogleDriveService service;

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable String id) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); // Stream para receber arquivo
        service.downloadFile(id, outputStream); // Faz download para a stream

        byte[] content = outputStream.toByteArray(); // Converte stream para byte[]

        return ResponseEntity.ok() // Retorna 200 OK
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // Tipo genérico de arquivo
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"downloaded-file\"") // Define nome do arquivo baixado
                .body(content); // Conteúdo do arquivo
    }
}
