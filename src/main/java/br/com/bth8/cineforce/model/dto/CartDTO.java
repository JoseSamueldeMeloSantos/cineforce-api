package br.com.bth8.cineforce.model.dto;

import br.com.bth8.cineforce.model.entity.CartItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "carts")
public class CartDTO extends RepresentationModel<CartDTO> {


    private UUID id;

    @JsonProperty("total_price")
    private Double totalPrice;

    private List<CartItem> items;
}

