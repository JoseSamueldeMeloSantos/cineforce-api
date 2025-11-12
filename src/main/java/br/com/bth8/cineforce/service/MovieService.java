package br.com.bth8.cineforce.service;

import br.com.bth8.cineforce.model.dto.MovieDTO;
import br.com.bth8.cineforce.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    public MovieDTO create(MovieDTO dto) {
        return null;
    }
}
