package br.com.bth8.cineforce.service;

import br.com.bth8.cineforce.controller.MovieController;
import br.com.bth8.cineforce.exception.EnitityNotFoundException;
import br.com.bth8.cineforce.exception.EntityAlreadyExistsException;
import br.com.bth8.cineforce.mapper.ObjectMapper;
import br.com.bth8.cineforce.model.dto.MovieDTO;
import br.com.bth8.cineforce.model.entity.Movie;
import br.com.bth8.cineforce.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Slf4j
@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private PagedResourcesAssembler<MovieDTO> assembler;

    public MovieDTO create(MovieDTO movie) {
        log.info("creating movie");

        if (repository.findByName(movie.getName()).isPresent()) {
            new EntityAlreadyExistsException();
        }

        var enity = mapper.parseObject(movie, Movie.class);
        MovieDTO dto = mapper.parseObject(repository.save(enity),MovieDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    public PagedModel<EntityModel<MovieDTO>> findAll(Pageable pageable, String sortReference) {
        log.info("Finding all movies");

        var entities = repository.findAll(pageable);

        Page<MovieDTO> moviesDtoWithHateoas = entities.map(movie -> {
            MovieDTO dto = mapper.parseObject(movie, MovieDTO.class);
            try {
                addHateoasLinks(dto);
            } catch (EnitityNotFoundException e) {
                throw new RuntimeException(e);
            }
            return dto;
        });

        Link findAllLinks = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder
                .methodOn(MovieController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), String.valueOf(pageable.getSort()), sortReference))
                .withSelfRel();


        return assembler.toModel(moviesDtoWithHateoas, findAllLinks);
    }

    public MovieDTO findById(UUID id) {

        log.info("finding a movie by his ID");

        var entity = repository.findById(id).
                orElseThrow(() -> new EnitityNotFoundException("Movie Not Found Exception"));

        MovieDTO dto = mapper.parseObject(entity, MovieDTO.class);

        addHateoasLinks(dto);

        return dto;
    }
    
    public MovieDTO findByName(String name) {
        log.info("finding a movie by his ID");

        var entity = repository.findByName(name)
                .orElseThrow(() -> new EnitityNotFoundException("Movie Not Found Exception"));

        MovieDTO dto = mapper.parseObject(entity, MovieDTO.class);

        addHateoasLinks(dto);

        return dto;
    }


    public void delete(UUID id) {

        log.info("deteleting a movie by his ID");

        var entity = repository.findById(id)
                .orElseThrow(() -> new EnitityNotFoundException());

        repository.delete(entity);
    }


    public MovieDTO updatePartiality(UUID id, Map<String, Object> fields) {

        log.info("updating some fields");

        var movie = repository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException());

        fields.forEach((key,value) -> {
            Field field = ReflectionUtils.findField(Movie.class, key);
            if (field != null) {
                field.setAccessible(true);//permite modificação
                ReflectionUtils.setField(field, movie, value);
            }
        });

        MovieDTO dto = mapper.parseObject(repository.save(movie), MovieDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    public MovieDTO update(MovieDTO dto) {
        log.info("Updating a complete object");

        var entity = repository.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException());

        entity.setName(dto.getName());
        entity.setReleaseDate(dto.getReleaseDate());
        entity.setAgeRating(dto.getAgeRating());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setMovieCast(dto.getMovieCast());
        entity.setDirector(dto.getDirector());
        entity.setDuration(dto.getDuration());
        entity.setGenre(dto.getGenre());
        entity.setMovieLink(dto.getMovieLink());
        entity.setAdditionDate(dto.getAdditionDate());

        MovieDTO mdto = mapper.parseObject(repository.save(entity),MovieDTO.class);
        addHateoasLinks(mdto);

        return mdto;
    }

    private void  addHateoasLinks(MovieDTO dto) {

        dto.add(linkTo(methodOn(MovieController.class).findById(dto.getId())).withSelfRel().withType("GET"));

        dto.add(linkTo(methodOn(MovieController.class).findByName(dto.getName())).withRel("findByName").withType("GET"));

        dto.add(linkTo(methodOn(MovieController.class).create(dto)).withRel("create").withType("POST"));

        dto.add(linkTo(methodOn(MovieController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));

        dto.add(linkTo(methodOn(MovieController.class).updatePartiality(dto.getId(), Map.of())).withRel("updatePartiality").withType("PATCH"));
    }
}
