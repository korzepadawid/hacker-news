package io.github.korzepadawid.hackernewsapi.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.korzepadawid.hackernewsapi.common.exception.ErrorResponse;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);
    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final String responseJson =
                    objectMapper.writeValueAsString(
                            new ErrorResponse(HackerNewsError.UNAUTHORIZED));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
            response.getWriter().write(responseJson);
        } catch (JsonProcessingException exception) {
            log.error("Can't process JSON");
        }
    }
}
