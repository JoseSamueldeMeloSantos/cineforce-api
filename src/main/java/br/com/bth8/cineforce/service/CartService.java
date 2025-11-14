package br.com.bth8.cineforce.service;

import br.com.bth8.cineforce.controller.CartController;
import br.com.bth8.cineforce.exception.EntityAlreadyExistsException;
import br.com.bth8.cineforce.mapper.ObjectMapper;
import br.com.bth8.cineforce.model.dto.CartDTO;
import br.com.bth8.cineforce.model.dto.MovieDTO;
import br.com.bth8.cineforce.model.entity.CartItem;
import br.com.bth8.cineforce.model.entity.Movie;
import br.com.bth8.cineforce.repository.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Slf4j
public class CartService {

    @Autowired
    private CartRepository repository;
    @Autowired
    private ObjectMapper mapper;

    public CartDTO addItemToCart(MovieDTO movie, UUID cartId) {
        log.info("Adding card item to cart");

        CartItem item = new CartItem(1,mapper.parseObject(movie, Movie.class),movie.getPrice());

        var cart = repository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        if (cart.getItems().contains(item)) {
            cart.getItems().add(item);
        } else {
            new EntityAlreadyExistsException();
        }

        CartDTO dto = mapper.parseObject(repository.save(cart), CartDTO.class);
        addHateoasLinks(dto, movie);

        return dto;
    }

    private void addHateoasLinks(CartDTO dto, MovieDTO mDto)  {
        dto.add(linkTo(methodOn(CartController.class).addItemToCart(mDto,dto.getId())).withRel("addItemToCart").withType("POST"));
    }
}
