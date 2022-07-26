package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.auth.CurrentUser;
import io.github.korzepadawid.hackernewsapi.common.projection.UserRead;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    public UserRead findCurrentUser(final @CurrentUser UserDetails userDetails) {
        return userService.findUserByEmail(userDetails.getUsername());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/me/avatar")
    public void setAvatarForCurrentUser(final @CurrentUser UserDetails userDetails,
                                        final @RequestParam("file") MultipartFile file) {
        userService.setAvatarByEmail(userDetails.getUsername(), file);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/me/avatar")
    public void deleteAvatarForCurrentUser(final @CurrentUser UserDetails userDetails) {
        userService.deleteAvatarByEmail(userDetails.getUsername());
    }
}
