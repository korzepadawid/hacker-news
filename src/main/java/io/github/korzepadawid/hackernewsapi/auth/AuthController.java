package io.github.korzepadawid.hackernewsapi.auth;

import io.github.korzepadawid.hackernewsapi.user.UserRead;
import io.github.korzepadawid.hackernewsapi.user.UserWrite;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
class AuthController {

    private final AuthService authService;

    AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRead signUp(@RequestBody @Valid final UserWrite userWrite) {
        return authService.signUp(userWrite);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public AuthDetails signIn(@RequestBody @Valid final AuthCredentials authCredentials) {
        return authService.signIn(authCredentials);
    }
}
