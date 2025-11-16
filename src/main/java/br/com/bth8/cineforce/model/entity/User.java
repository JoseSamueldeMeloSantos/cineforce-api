package br.com.bth8.cineforce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"cart", "purchasedMovies"})
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @NotNull
    @Column(name = "nick_name",nullable = false)
    private String nickName;

    @Email
    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 60)
    private String bio;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ManyToMany
    @JoinTable(
            name = "user_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> purchasedMovies;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    public User(String nickName, String email, String bio, LocalDate birthDate) {
        this.nickName = nickName;
        this.email = email;
        this.bio = bio;
        this.birthDate = birthDate;
        this.purchasedMovies = new ArrayList<>();
    }
}
