package com.example.springbootkeycloack.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    private final JwtAuthConverterProperties properties;
    Logger log = LoggerFactory.getLogger(JwtAuthConverter.class);

    public JwtAuthConverter(JwtAuthConverterProperties properties) {
        this.properties = properties;
    }
    @Override
    public AbstractAuthenticationToken convert(Jwt token) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(token).stream(),
                extractResourceRoles(token).stream()).collect(Collectors.toSet());
        log.debug("JWT Token: {}", token.getTokenValue());
        log.debug("JWT Claims: {}", token.getClaims());
        return new JwtAuthenticationToken(token, authorities,getPrincipalClaimName(token));
    }

    private String getPrincipalClaimName(Jwt token) {
        String claimName = JwtClaimNames.SUB;
        if (properties.getPrincipalAttribute() != null) {
            claimName = properties.getPrincipalAttribute();
        }
        return token.getClaim(claimName);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt token) {
        Map<String, Object> resourceAccess = token.getClaim("resource_access");
        Map<String, Object> resource;
        Collection<String> resourceRoles;
        if (resourceAccess == null
            || (resource = (Map<String, Object>) resourceAccess.get(properties.getResourceId())) == null
            || (resourceRoles = (Collection<String>) resource.get("roles")) == null) {
            return Set.of();
        }
        if (resourceRoles != null) {
            log.debug("Extracted roles from JWT: {}", resourceRoles);
        }
        return resourceRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }


}
