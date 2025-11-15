package br.com.bth8.cineforce.service;

import br.com.bth8.cineforce.controller.CartController;
import br.com.bth8.cineforce.exception.EntityAlreadyExistsException;
import br.com.bth8.cineforce.mapper.ObjectMapper;
import br.com.bth8.cineforce.model.dto.CartDTO;
import br.com.bth8.cineforce.model.dto.CartItemDTO;
import br.com.bth8.cineforce.model.dto.MovieDTO;
import br.com.bth8.cineforce.model.entity.CartItem;
import br.com.bth8.cineforce.model.entity.Movie;
import br.com.bth8.cineforce.repository.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

        CartItem item = new CartItem(movie.getId(),1,mapper.parseObject(movie, Movie.class),movie.getPrice());

        var cart = repository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        if (cart.getItems().contains(item)) {
            cart.getItems().add(item);
        } else {
            new EntityAlreadyExistsException("Movie already exists in the cart");
        }

        CartDTO dto = mapper.parseObject(repository.save(cart), CartDTO.class);
        addHateoasLinks(dto, movie);

        return dto;
    }

    public CartDTO updateItemQuantity(CartDTO cartDTO, UUID itemId, Integer quantity) {

        var cart = repository.findById(cartDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        CartItem item = cart.getItemById(itemId);

        item.setQuantity(quantity);

        CartDTO dto = mapper.parseObject(repository.save(cart),CartDTO.class);

        addHateoasLinks(dto, new MovieDTO());

        return dto;
    }

    public List<CartItemDTO> getAllCardItems(UUID id) {
        var cart = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        List<CartItemDTO> listDto = mapper.parseListObjects(cart.getItems(), CartItemDTO.class);

        listDto.forEach((item) -> {
            addCardItemHateoasLinks(item, new CartDTO(), new MovieDTO());
        });

        return listDto;
    }

    private void addCardItemHateoasLinks(CartItemDTO dto, CartDTO cartDTO, MovieDTO mDto)  {
        dto.add(linkTo(methodOn(CartController.class).addItemToCart(mDto,cartDTO.getId())).withRel("addItemToCart").withType("POST"));
        dto.add(linkTo(methodOn(CartController.class).updateItemQuantity(cartDTO, UUID.randomUUID(), 1)).withRel("updateItemQuantity").withType("PATCH"));
        dto.add(linkTo(methodOn(CartController.class).getAllCardItems(cartDTO.getId())).withRel("getAllCardItems").withType("GET"));
    }

    private void addHateoasLinks(CartDTO dto, MovieDTO mDto)  {
        dto.add(linkTo(methodOn(CartController.class).addItemToCart(mDto,dto.getId())).withRel("addItemToCart").withType("POST"));
        dto.add(linkTo(methodOn(CartController.class).updateItemQuantity(dto, UUID.randomUUID(), 1)).withRel("updateItemQuantity").withType("PATCH"));
    }

}
