package br.com.bth8.cineforce.model.entity;

import jakarta.persistence.*;
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
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "age_rating")
    private String ageRating;

    @Column(name = "movie_link")
    private String movieLink;

    @Column(name = "addition_date")
    private LocalDate additionDate;

    @Column
    private String genre;

    @Column
    private String description;

    @NotNull
    @Column(nullable = false)
    private Double price;

    @Column(name = "movie_cast")
    private String[] movieCast;

    @Column
    private  String director;

    @Column
    private String duration;


}
