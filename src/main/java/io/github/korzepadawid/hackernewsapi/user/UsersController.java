package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.auth.CurrentUser;
import io.github.korzepadawid.hackernewsapi.common.projection.UserRead;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @PutMapping(path = "/me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void setAvatarByEmail(final @CurrentUser @Parameter(hidden = true) UserDetails userDetails,
                                 final @RequestParam("file") MultipartFile file) {
        userService.setAvatarByEmail(userDetails.getUsername(), file);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/verify/{verificationToken}")
    public void verifyUserEmailWithToken(@PathVariable final String verificationToken) {
        userService.verifyUserEmailWithToken(verificationToken);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    public UserRead findCurrentUser(final @CurrentUser @Parameter(hidden = true) UserDetails userDetails) {
        return userService.findUserByEmailDto(userDetails.getUsername());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/me/avatar")
    public void deleteAvatarForCurrentUser(final @CurrentUser @Parameter(hidden = true) UserDetails userDetails) {
        userService.deleteAvatarByEmail(userDetails.getUsername());
    }
}
