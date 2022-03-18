package com.luisdias.rmmservice.infra.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenParser jwtTokenParser;

    public JwtAuthenticationFilter(JwtTokenParser jwtTokenParser) {
        this.jwtTokenParser = jwtTokenParser;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        var maybeAuth = getAuthentication(authHeader.split(" ")[1].trim());

        if (maybeAuth.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(maybeAuth.get());
        filterChain.doFilter(request, response);
    }

    private Optional<JwtUserToken> getAuthentication(String requestToken) {
        try {
            return jwtTokenParser.parseJwtToken(requestToken);
        } catch (ExpiredJwtException exception) {
            logger.warn("Request to parse expired JWT failed : {}", exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            logger.warn("Request to parse unsupported JWT failed : {}", exception.getMessage());
        } catch (MalformedJwtException exception) {
            logger.warn("Request to parse invalid JWT failed : {}", exception.getMessage());
        } catch (SignatureException exception) {
            logger.warn("Request to parse JWT with invalid signature failed : {}", exception.getMessage());
        } catch (IllegalArgumentException exception) {
            logger.warn("Request to parse empty or null JWT failed : {}", exception.getMessage());
        }

        return Optional.empty();
    }
}
