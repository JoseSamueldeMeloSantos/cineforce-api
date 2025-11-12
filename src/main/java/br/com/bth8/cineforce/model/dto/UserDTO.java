package br.com.bth8.cineforce.model.dto;

import br.com.bth8.cineforce.model.entity.Movie;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserDTO {

    private UUID id;

    @JsonProperty("nick_name")
    private String nickName;

    private String email;

    private String bio;

    @JsonProperty("birth_date")
    @JsonFormat(timezone = "dd/mm/yyyy")
    private LocalDate birthDate;

    @JsonProperty("purchased_movies")
    private Set<Movie> purchasedMovies;
}
