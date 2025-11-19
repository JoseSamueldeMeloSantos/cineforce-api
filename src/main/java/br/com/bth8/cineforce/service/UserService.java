package br.com.bth8.cineforce.service;

import br.com.bth8.cineforce.controller.UserController;
import br.com.bth8.cineforce.exception.EnitityNotFoundException;
import br.com.bth8.cineforce.model.mapper.ObjectMapper;
import br.com.bth8.cineforce.model.dto.UserDTO;
import br.com.bth8.cineforce.model.entity.Cart;
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

    public UserDTO create(UserDTO userDTO) {
        log.info("creating user");

        User user = new User(userDTO.getNickName(), userDTO.getEmail(), userDTO.getBio(), userDTO.getBirthDate());

        User savedUser = repository.save(user);
        savedUser.setCart(new Cart(savedUser, 0.0));
        repository.save(savedUser);

        UserDTO dto = mapper.parseObject(savedUser, UserDTO.class);

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
                .orElseThrow(() -> new EnitityNotFoundException("user Not Found Exception"));

        repository.delete(entity);
    }


    public UserDTO updatePartiality(UUID id, Map<String, Object> fields) {

        var user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user Not Found Exception"));

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
