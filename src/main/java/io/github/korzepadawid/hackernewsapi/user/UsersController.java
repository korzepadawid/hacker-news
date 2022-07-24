package io.github.korzepadawid.hackernewsapi.user;

import org.springframework.http.HttpStatus;
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
    public void verifyEmail(@PathVariable final String verificationToken) {
        userService.verifyUserWithToken(verificationToken);
    }
}
