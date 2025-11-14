package br.com.bth8.cineforce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CartItem {


    @Column
    private Integer quantity;

    @ManyToOne (optional = false)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column
    private Double subTotal;
}
