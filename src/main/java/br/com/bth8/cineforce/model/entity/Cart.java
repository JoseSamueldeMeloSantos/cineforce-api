package br.com.bth8.cineforce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"items"})
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    private UUID id;

    @OneToOne
    @MapsId // FAZ O CART RECEBER O MESMO ID DO USER
    @JoinColumn(name = "id")
    private User user;

    @Column(nullable = false)
    private Double totalPrice = 0.0;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private List<CartItem> items = new ArrayList<>();

    public Cart(User user, Double totalPrice) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.items = new ArrayList<>();
    }

    public CartItem getItemById(UUID id) {
        return items.stream()
                .filter(i -> i.getMovie().getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException());
    }

}
