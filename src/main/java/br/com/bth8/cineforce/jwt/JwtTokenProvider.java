package br.com.bth8.cineforce.jwt;

import br.com.bth8.cineforce.exception.InvalidJwtAuthenticationException;
import br.com.bth8.cineforce.model.dto.security.TokenDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
        algorithm = Algorithm.HMAC256(secretKey.getBytes());//cria o algoritmo HMAC256 para assinar tokens
    }

    public TokenDTO createAcessToken(String username, List<String> roles) {

        Instant now = Instant.now();
        Instant validity = now.plusMillis(validityInMilliseconds);

        String accessToken = getAcessToken(username, roles, now, validity);
        String refreshToken = getRefreshToken(username, roles, now);

        return new TokenDTO(username,true, now, validity, accessToken, refreshToken);
    }

    private String getRefreshToken(String username, List<String> roles, Instant now) {

        Instant refreshTokenValidity = Instant.now().plusMillis(validityInMilliseconds * 3);

        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)//até quando vai durar
                .withExpiresAt(refreshTokenValidity)
                .withSubject(username)
                .sign(algorithm);
    }

    private String getAcessToken(String username, List<String> roles, Instant now, Instant validity) {

        String issueUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        return JWT.create()
                .withClaim("roles",roles)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withSubject(username)
                .withIssuer(issueUrl)//para identificar quem gerou o token
                .sign(algorithm);//Cria a assinatura HMAC256 com a secretKey
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        UserDetails userDetails = this.userDetailsService
                .loadUserByUsername(decodedJWT.getSubject());//busca no banco  um usuario e retorna se um obj userDetails

        //cria um obj de autenticação do spring security
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());//user,senha,permissões
    }

    //verifica,valida e docodifica
    private DecodedJWT decodedToken(String token) {
        //Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());//cria o algoritimo de verificação
        JWTVerifier verifier = JWT.require(algorithm).build();//Cria um verificador de tokens JWT com base no alg
        DecodedJWT decodedJWT = verifier.verify(token);//verifica e valida o token

        return decodedJWT;
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }

        return null;
    }

    public boolean validatedToken(String token) {
        DecodedJWT decodedJWT = decodedToken(token);

        try {
            if (decodedJWT.getExpiresAt().toInstant().isBefore(Instant.now())) {
                return false;
            }

            return true;
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Expire or Invalid JWT Token");
        }
    }

}
