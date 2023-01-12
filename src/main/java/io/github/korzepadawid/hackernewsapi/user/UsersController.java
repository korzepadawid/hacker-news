package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.auth.CurrentUser;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
class UsersController {

    private final UserService userService;

    UsersController(final UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/verify/{verificationToken}")
    public void verifyUserEmailWithToken(@PathVariable final String verificationToken) {
        userService.verifyUserEmailWithToken(verificationToken);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    public UserRead findCurrentUser(final @CurrentUser @Parameter(hidden = true) UserDetails userDetails) {
        return userService.findUserByEmailDto(userDetails.getUsername());
    }
}
