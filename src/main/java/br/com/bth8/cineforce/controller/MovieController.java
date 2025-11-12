package br.com.bth8.cineforce.controller;

import br.com.bth8.cineforce.exception.EnitityNotFoundException;
import br.com.bth8.cineforce.model.dto.MovieDTO;
import br.com.bth8.cineforce.model.entity.Movie;
import br.com.bth8.cineforce.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("api/movie")
public class MovieController {

    @Autowired
    private MovieService service;

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
    public ResponseEntity<MovieDTO> create(@RequestBody MovieDTO dto) {
        MovieDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created); // 201 Created
    }

    @GetMapping(
            value = "/id/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )
    public ResponseEntity<MovieDTO> findById(@PathVariable UUID id) throws EnitityNotFoundException {
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
    public ResponseEntity<MovieDTO> findByName(@PathVariable String name) throws EnitityNotFoundException {
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

    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws EnitityNotFoundException {
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
