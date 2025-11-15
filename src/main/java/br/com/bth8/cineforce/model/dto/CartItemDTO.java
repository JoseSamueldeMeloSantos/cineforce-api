package br.com.bth8.cineforce.model.dto;

import br.com.bth8.cineforce.model.entity.Movie;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "items")
public class CartItemDTO extends RepresentationModel<CartItemDTO> {


    private Integer quantity;

    private Movie movie;

    @JsonProperty("sub_total")
    private Double subTotal;
}
