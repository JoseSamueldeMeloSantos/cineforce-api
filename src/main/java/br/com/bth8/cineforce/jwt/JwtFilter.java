package br.com.bth8.cineforce.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final JwtTokenProvider tokenProvider;

    @Override
    public void doFilter(//O doFilter define como cada requisição será processada e possivelmente bloqueada ou modificada antes de chegar ao endpoint da aplicação.
            ServletRequest request,
            ServletResponse response,
            FilterChain filter) throws IOException, ServletException {

        var token = tokenProvider.resolveToken((HttpServletRequest) request);

        if (StringUtils.isNotBlank(token) && tokenProvider.validatedToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);

            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);//adiciona no contexto de segurança do Spring
            }
        }

        // Continua a requisição para o próximo filtro ou endpoint
        filter.doFilter(request, response);
    }
}
