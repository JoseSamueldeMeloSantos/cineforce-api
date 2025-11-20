package br.com.bth8.cineforce.model.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TokenDTO {

    private String username;
    private String password;
    private Boolean authenticated;
    private LocalDateTime created;
    private LocalDateTime expiration;
    private String acessToken;
    private String refreshToken;//quando o token expirar
}
