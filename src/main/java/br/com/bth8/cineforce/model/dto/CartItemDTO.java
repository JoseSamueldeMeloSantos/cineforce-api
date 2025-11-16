package br.com.bth8.cineforce.model.dto;

import br.com.bth8.cineforce.model.entity.Movie;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id","quantity", "subTotal"})
@Relation(itemRelation = "items")
public class CartItemDTO extends RepresentationModel<CartItemDTO> {

    private UUID id;

    private Integer quantity;

    private MovieDTO movie;

    @JsonProperty("sub_total")
    private Double subTotal;
}
