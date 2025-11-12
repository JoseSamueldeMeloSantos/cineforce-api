package br.com.bth8.cineforce.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

    private UUID id;

    private String name;

    @JsonProperty("release_date")
    private LocalDate releaseDate;

    @JsonProperty("age_rating")
    private String ageRating;

    private String description;

    private Double price;

    private Set<String> cast;

    private  String director;

    private String duration;

}
