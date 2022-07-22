package io.github.korzepadawid.hackernewsapi.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Configuration
class SecurityConfig implements SecurityFilterChain {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public boolean matches(final HttpServletRequest request) {
        return true;
    }

    @Override
    public List<Filter> getFilters() {
        return Collections.emptyList();
    }
}
