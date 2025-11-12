package br.com.bth8.cineforce.service;

import br.com.bth8.cineforce.exception.EnitityNotFoundException;
import br.com.bth8.cineforce.exception.EntityAlreadyExistsException;
import br.com.bth8.cineforce.mapper.ObjectMapper;
import br.com.bth8.cineforce.model.dto.MovieDTO;
import br.com.bth8.cineforce.model.entity.Movie;
import br.com.bth8.cineforce.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;
    @Autowired
    private ObjectMapper mapper;

    public MovieDTO create(MovieDTO movie) {
        log.info("creating movie");

        if (repository.findByName(movie.getName()).isPresent()) {
            new EntityAlreadyExistsException();
        }

        Movie enity = mapper.parseObject(movie, Movie.class);
        MovieDTO dto = mapper.parseObject(repository.save(enity),MovieDTO.class);

        return dto;
    }

    public MovieDTO findById(UUID id) throws EnitityNotFoundException {

        log.info("finding a movie by his ID");

        Movie entity = repository.findById(id).
                orElseThrow(() -> new EnitityNotFoundException("Movie Not Found Exception"));

        MovieDTO dto = mapper.parseObject(entity, MovieDTO.class);

        return dto;
    }


}
