package br.com.bth8.cineforce.jwt;

import br.com.bth8.cineforce.model.dto.security.TokenDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {//O provider lida diretamente com o token em si — sua geração e verificação. Ele normalmente faz:

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;
    @Value("${security.jwt.token.expire-lenght}")
    private long validityInMilliseconds;

    private final UserDetailsService userDetailsService;
    private Algorithm algorithm;

    @PostConstruct
    protected void  init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());//codifica a secretKey em Base64
        algorithm = algorithm.HMAC256(secretKey.getBytes());//cria o algoritmo HMAC256 para assinar tokens
    }

    public TokenDTO createAcessToken(String username, List<String> roles) {

        Instant now = Instant.now();
        Instant validity = now.plusMillis(validityInMilliseconds);

        String accessToken = getAcessToken(username, roles, now, validity);
        String refreshToken = getRefreshToken(username, roles, now);

        return new TokenDTO(username,true, now, validity, accessToken, refreshToken);
    }

    private String getRefreshToken(String username, List<String> roles, Instant now) {
        return null;
    }

    private String getAcessToken(String username, List<String> roles, Instant now, Instant validity) {
        return null;
    }


}
