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
            value = "/add/{id}",
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
    public ResponseEntity<CartDTO> addItemToCart(@RequestBody MovieDTO movie, @PathVariable("id") UUID cartId) {
        return ResponseEntity.ok(service.addItemToCart(movie, cartId));
    }

    @PatchMapping(
            value = "/cart/increase/{itemId}/{quantity}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )
    public ResponseEntity<CartDTO> updateItemQuantity(
            @RequestBody CartDTO cartDTO,          // Objeto vindo do corpo da requisição
            @PathVariable("itemId") UUID itemId,  // ID do item a ser atualizado
            @PathVariable("quantity") Integer quantity  // Quantidade a aumentar
    ) {
        CartDTO updatedCart = service.updateItemQuantity(cartDTO, itemId, quantity);
        return ResponseEntity.ok(updatedCart);
    }
}
