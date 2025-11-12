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
import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @NotNull
    @Column(name = "nick_name",nullable = false, unique = true)
    private String nickName;

    @Email
    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 60)
    private String bio;

    @Column
    private LocalDate birthDate;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "purchased_movies_fk")
    private Set<Movie> purchasedMovies;
}
