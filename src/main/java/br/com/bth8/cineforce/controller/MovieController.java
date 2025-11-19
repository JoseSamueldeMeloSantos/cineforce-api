package br.com.bth8.cineforce.controller;

import br.com.bth8.cineforce.exception.EnitityNotFoundException;
import br.com.bth8.cineforce.model.dto.MovieDTO;
import br.com.bth8.cineforce.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )
    public ResponseEntity<PagedModel<EntityModel<MovieDTO>>> findAll(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "15") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        @RequestParam(value = "sort_Reference", defaultValue = "name") String sortReference
    ) {
        Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;


        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortReference));

        return ResponseEntity.ok(service.findAll(pageable, sortReference));
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, // aceita multipart/form-data
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )
    public ResponseEntity<MovieDTO> create(
            @RequestPart("movie") MovieDTO dto,   // parte JSON do DTO
            @RequestPart("file") MultipartFile file // parte arquivo
    ) {
        MovieDTO created = service.create(dto, file);
        return ResponseEntity.status(201).body(created);
    }


    @GetMapping(
            value = "/id/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )
    public ResponseEntity<MovieDTO> findById(@PathVariable UUID id) {
        MovieDTO movie = service.findById(id);
        return ResponseEntity.ok(movie); // 200 OK
    }

    @GetMapping(
            value = "/name/{name}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )
    public ResponseEntity<MovieDTO> findByName(@PathVariable String name) {
        MovieDTO movie = service.findByName(name);
        return ResponseEntity.ok(movie);
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
    public ResponseEntity<MovieDTO> updatePartiality(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> fields
    ) {
        MovieDTO updated = service.updatePartiality(id, fields);
        return ResponseEntity.ok(updated); // 200 OK
    }

    @PutMapping(
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
    public ResponseEntity<MovieDTO> update(MovieDTO dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }



}
