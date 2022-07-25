package io.github.korzepadawid.hackernewsapi.auth;


interface JwtService {

    String generateToken(String id);

    Boolean verify(String jwt);

    String extractIssuerId(String jwt);
}
