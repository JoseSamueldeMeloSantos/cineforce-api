package br.com.bth8.cineforce.config;

import br.com.bth8.cineforce.jwt.JwtTokenFilter;
import br.com.bth8.cineforce.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity//Ativa as funcionalidades do Spring Security no projeto.
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;


    @Bean
    PasswordEncoder passwordEncoder() {//sse método define o padrão de criptografia da aplicação

        // Cria um encoder PBKDF2 (forte e recomendado).
        PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder(
                "", 8, 185000,// vazio para gerar automaticamente  / comprimento da chave /numero de vezes que o algoritimo vai ser aplicado
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256//algoritimo de hash(criptografia)
        );

        // Mapa de encoders suportados. Ele permite que no futuro outros algoritmos sejam adicionados.
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", pbkdf2Encoder);// Registra o encoder PBKDF2 com o identificador "pbkdf2"


                                                        //nome do algoritmo q a gente vai utilizar / hash de algoritimo q a gente vai usar
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2",encoders);

        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);// Define o encoder padrão para validação/codificação

        return passwordEncoder;
    }

    @Bean                                       //o AuthenticationConfiguration sabe como criar/configurar o AuthenticationManeger
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        JwtTokenFilter filter = new JwtTokenFilter(tokenProvider);

        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(
                        ssesion -> ssesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers(
                                        "/auth/sigin",
                                        "/auth/refresh/**",
                                        "/auth/createUser",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                ).permitAll()
                                .requestMatchers("/api/**").authenticated()
                                .requestMatchers("/users").denyAll()
                )
                .cors(cors -> {})
                .build();
    }

}
