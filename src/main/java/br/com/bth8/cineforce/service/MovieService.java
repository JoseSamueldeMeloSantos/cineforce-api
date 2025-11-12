package br.com.bth8.cineforce.service;

import br.com.bth8.cineforce.mapper.ObjectMapper;
import br.com.bth8.cineforce.model.dto.MovieDTO;
import br.com.bth8.cineforce.model.entity.Movie;
import br.com.bth8.cineforce.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;
    @Autowired
    private ObjectMapper mapper;

    public MovieDTO create(MovieDTO movie) {

        if (repository.findByName(movie.getName()).isPresent()) {
            log.warn("Movie Already exist");
        }

        Movie enity = mapper.parseObject(movie, Movie.class);
        MovieDTO dto = mapper.parseObject(repository.save(enity),MovieDTO.class);

        return dto;
    }
}
