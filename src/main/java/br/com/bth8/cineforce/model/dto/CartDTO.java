package br.com.bth8.cineforce.model.dto;

import br.com.bth8.cineforce.model.entity.CartItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class CartDTO {


    private UUID uuid;

    private Double totalPrice;

    private List<CartItem> items = new ArrayList<>();
}

