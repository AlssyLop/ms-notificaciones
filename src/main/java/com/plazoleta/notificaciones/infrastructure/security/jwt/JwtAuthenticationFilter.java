package com.plazoleta.notificaciones.infrastructure.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                      HttpServletResponse response,
                                      FilterChain filterChain) throws ServletException, IOException {
        String token = obtenerToken(request);

        if (token != null && jwtTokenProvider.validarToken(token)) {
            Long idUsuario = jwtTokenProvider.obtenerIdUsuario(token);
            String rol = jwtTokenProvider.obtenerRol(token);
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol));
            var auth = new UsernamePasswordAuthenticationToken(idUsuario, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
            JwtTokenHolder.setToken(token);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            JwtTokenHolder.clear();
        }
    }

    private String obtenerToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
