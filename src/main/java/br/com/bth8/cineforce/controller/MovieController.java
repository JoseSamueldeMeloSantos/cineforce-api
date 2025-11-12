package br.com.bth8.cineforce.controller;

import br.com.bth8.cineforce.exception.EnitityNotFoundException;
import br.com.bth8.cineforce.model.dto.MovieDTO;
import br.com.bth8.cineforce.model.entity.Movie;
import br.com.bth8.cineforce.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public MovieDTO create(@RequestBody MovieDTO dto) {
        return service.create(dto);
    }

    @GetMapping(
            value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )
    public MovieDTO findById(@PathVariable("id") UUID id)
            throws EnitityNotFoundException {
        return service.findById(id);
    }

    @GetMapping(
            value = "/{name}",
            produces = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
                MediaType.APPLICATION_YAML_VALUE
            }
    )
    public MovieDTO findByName(@PathVariable("name") String name) throws EnitityNotFoundException {
        return service.findByName(name);
    }

    @DeleteMapping(
            value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) throws EnitityNotFoundException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
