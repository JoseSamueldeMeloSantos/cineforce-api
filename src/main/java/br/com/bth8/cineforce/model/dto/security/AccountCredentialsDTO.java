package br.com.bth8.cineforce.model.dto.security;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AccountCredentialsDTO {//recebe do navegador

    private String username;
    private String password;
}
