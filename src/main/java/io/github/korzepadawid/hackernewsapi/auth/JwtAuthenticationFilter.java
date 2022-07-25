package io.github.korzepadawid.hackernewsapi.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String HTTP_AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private static final int BEARER_TOKEN_PREFIX_LENGTH = 7;

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    JwtAuthenticationFilter(final JwtService jwtService,
                            final UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        final String jwtFromRequest = extractJwtFromRequest(request);

        if (StringUtils.hasText(jwtFromRequest) && jwtService.verify(jwtFromRequest)) {
            final String issuerId = jwtService.extractIssuerId(jwtFromRequest);
            final UserDetails principal = userDetailsService.loadUserByUsername(issuerId);
            final UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            logger.error("cant authenticate the user");
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(final HttpServletRequest httpServletRequest) {
        final String header = httpServletRequest.getHeader(HTTP_AUTHORIZATION_HEADER);

        if (StringUtils.hasText(header) && header.startsWith(BEARER_TOKEN_PREFIX)) {
            return header.substring(BEARER_TOKEN_PREFIX_LENGTH);
        } else {
            logger.error("can't extract jwt");
        }

        return "";
    }
}
