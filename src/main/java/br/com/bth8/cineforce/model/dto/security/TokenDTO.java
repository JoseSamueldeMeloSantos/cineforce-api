package br.com.bth8.cineforce.model.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TokenDTO {

    private String username;
    private Boolean authenticated;
    private Instant created;
    private Instant expiration;
    private String acessToken;
    private String refreshToken;//quando o token expirar
}
