package com.neobis.g4g.girls_for_girls.security;

import com.neobis.g4g.girls_for_girls.data.dto.TokenDTO;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class TokenGenerator {

    private final JwtEncoder accessTokeEncoder;
    private final JwtEncoder refreshTokenEncoder;

    @Autowired
    public TokenGenerator(JwtEncoder accessTokeEncoder,
                          @Qualifier("jwtRefreshTokenEncoder") JwtEncoder refreshTokenEncoder) {
        this.accessTokeEncoder = accessTokeEncoder;
        this.refreshTokenEncoder = refreshTokenEncoder;
    }

    private String createAccessToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("g4g")
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.MINUTES))
                .subject(Long.toString(user.getId()))
                .claim("username", user.getUsername())
                .build();

        return accessTokeEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    private String createRefreshToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("g4g")
                .issuedAt(now)
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .subject(Long.toString(user.getId()))
                .claim("username", user.getUsername())
                .build();

        return refreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    public TokenDTO createToken(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof User user)) {
            throw new BadCredentialsException(MessageFormat.format("principal {0} is not of User type",
                    authentication.getPrincipal().getClass()));
        }

        TokenDTO tokenDTO = new TokenDTO();

        tokenDTO.setUserId(user.getId());
        tokenDTO.setAccessToken(createAccessToken(authentication));
        tokenDTO.setRefreshToken(createRefreshToken(authentication));

        return tokenDTO;
    }
}
