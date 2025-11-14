package br.com.bth8.cineforce.controller;

import br.com.bth8.cineforce.model.dto.CartDTO;
import br.com.bth8.cineforce.model.dto.MovieDTO;
import br.com.bth8.cineforce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/cart")
public class CartController {

    @Autowired
    private CartService service;

    @PostMapping(
            value = "/add",
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
    public ResponseEntity<CartDTO> addItemToCart(@RequestBody MovieDTO movie, UUID cartId) {
        return ResponseEntity.ok(service.addItemToCart(movie, cartId));
    }
}
