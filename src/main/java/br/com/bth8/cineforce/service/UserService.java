package br.com.bth8.cineforce.service;

import br.com.bth8.cineforce.controller.MovieController;
import br.com.bth8.cineforce.controller.UserController;
import br.com.bth8.cineforce.exception.EnitityNotFoundException;
import br.com.bth8.cineforce.exception.EntityAlreadyExistsException;
import br.com.bth8.cineforce.mapper.ObjectMapper;
import br.com.bth8.cineforce.model.dto.MovieDTO;
import br.com.bth8.cineforce.model.dto.UserDTO;
import br.com.bth8.cineforce.model.entity.User;
import br.com.bth8.cineforce.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Slf4j
public class UserService {
    
    @Autowired
    private UserRepository repository;
    @Autowired
    private ObjectMapper mapper;

    public UserDTO create(UserDTO user) {
        log.info("creating user");

        if (repository.findById(user.getId()).isPresent()) {
            new EntityAlreadyExistsException();
        }

        var entity = mapper.parseObject(user, User.class);
        UserDTO dto = mapper.parseObject(repository.save(entity),UserDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    public UserDTO findById(UUID id) {

        log.info("finding a user by his ID");

        var entity = repository.findById(id).
                orElseThrow(() -> new EnitityNotFoundException("user Not Found Exception"));

        UserDTO dto = mapper.parseObject(entity, UserDTO.class);

        addHateoasLinks(dto);

        return dto;
    }


    public void delete(UUID id) {

        log.info("deteleting a user by his ID");

        var entity = repository.findById(id)
                .orElseThrow(() -> new EnitityNotFoundException());

        repository.delete(entity);
    }


    public UserDTO updatePartiality(UUID id, Map<String, Object> fields) {

        var user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        fields.forEach((key,value) -> {
            Field field = ReflectionUtils.findField(User.class, key);
            if (field != null) {
                field.setAccessible(true);//permite modificação
                ReflectionUtils.setField(field, user, value);
            }
        });

        UserDTO dto =  mapper.parseObject(repository.save(user), UserDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    private void  addHateoasLinks(UserDTO dto) {

        dto.add(linkTo(methodOn(UserController.class).findById(dto.getId())).withSelfRel().withType("GET"));

        dto.add(linkTo(methodOn(UserController.class).create(dto)).withRel("create").withType("POST"));

        dto.add(linkTo(methodOn(UserController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));

        dto.add(linkTo(methodOn(UserController.class).updatePartiality(dto.getId(), Map.of())).withRel("updatePartiality").withType("PATCH"));
    }
}
