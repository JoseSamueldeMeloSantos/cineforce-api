package br.com.bth8.cineforce.controller;

import br.com.bth8.cineforce.exception.EnitityNotFoundException;
import br.com.bth8.cineforce.model.dto.UserDTO;
import br.com.bth8.cineforce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService service;

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO dto) {
        UserDTO created = service.create(dto);
        return ResponseEntity.ok(created); // 201 Created
    }

    @GetMapping(
            value = "/id/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )
    public ResponseEntity<UserDTO> findById(@PathVariable UUID id) {
        UserDTO movie = service.findById(id);
        return ResponseEntity.ok(movie); // 200 OK
    }

    @PatchMapping(
            value = "/{id}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )
    public ResponseEntity<UserDTO> updatePartiality(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> fields
    ) {
        UserDTO updated = service.updatePartiality(id, fields);
        return ResponseEntity.ok(updated); // 200 OK
    }

    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
