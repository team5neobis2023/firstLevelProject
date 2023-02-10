package com.neobis.g4g.girls_for_girls.security;

import com.neobis.g4g.girls_for_girls.data.entity.UserEntity;
import com.neobis.g4g.girls_for_girls.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
    private final UserManager userManager;

    @Autowired
    public JwtToUserConverter(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        UserEntity user = (UserEntity) userManager.loadUserByUsername(jwt.getClaimAsString("username"));

        return new UsernamePasswordAuthenticationToken(user, jwt, user.getAuthorities());
    }
}
