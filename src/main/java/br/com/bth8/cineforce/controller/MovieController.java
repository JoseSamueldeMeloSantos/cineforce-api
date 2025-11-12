package br.com.bth8.cineforce.controller;

import br.com.bth8.cineforce.model.dto.MovieDTO;
import br.com.bth8.cineforce.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
            }
    )
    public MovieDTO create(@RequestBody MovieDTO dto) {

    }
}
