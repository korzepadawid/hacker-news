package io.github.korzepadawid.hackernewsapi.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
class JwtServiceImpl implements JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtServiceImpl.class);
    private static final Long EXPIRATION_IN_SECONDS = 3600L;
    private static final Long SECOND_IN_MILLIS = 1000L;
    @Value("${jwt.config.secret}")
    private String jwtSecret;

    @Override
    public String generateToken(final String email) {
        final long expiringAt = System.currentTimeMillis() + EXPIRATION_IN_SECONDS * SECOND_IN_MILLIS;
        return JWT.create()
                .withIssuer(email)
                .withExpiresAt(new Date(expiringAt))
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    @Override
    public Boolean verify(final String jwt) {
        try {
            decodedJWT(jwt);
            return true;
        } catch (JWTVerificationException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public String extractIssuerId(final String jwt) {
        return decodedJWT(jwt).getIssuer();
    }

    private DecodedJWT decodedJWT(final String jwt) {
        final JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtSecret)).build();
        return jwtVerifier.verify(jwt);
    }
}
