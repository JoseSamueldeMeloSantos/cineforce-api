package br.com.bth8.cineforce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private Integer quantity = 0;

    @ManyToOne (optional = false)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column
    private Double subTotal = 0.0;

    public CartItem(Integer quantity, Movie movie, Double subTotal) {
        this.quantity = quantity;
        this.movie = movie;
        this.subTotal = movie.getPrice() * quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity += quantity;
        updateSubTotal();
    }

    public  Double getSubTotal() {
        return subTotal;
    }

    public void  updateSubTotal()  {
        this.subTotal = this.movie.getPrice() * quantity;
    }
}
