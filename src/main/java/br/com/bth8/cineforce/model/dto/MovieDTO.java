package br.com.bth8.cineforce.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "movies")
public class MovieDTO extends RepresentationModel<MovieDTO> {

    private UUID id;

    private String name;

    @JsonProperty("release_date")
    @JsonFormat(timezone = "dd/mm/yyyy")
    private LocalDate releaseDate;

    @JsonProperty("age_rating")
    private String ageRating;

    private String description;

    private Double price;

    private String[] movieCast;

    private  String director;

    private String duration;

}
