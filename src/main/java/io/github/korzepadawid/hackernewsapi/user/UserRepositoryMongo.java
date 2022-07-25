package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;


interface UserRepositoryMongo extends UserRepository, MongoRepository<User, String> {

    Optional<User> findUserByEmailIgnoreCase(final @NotBlank @Email @Size(max = 345) String email);

    Optional<User> findUserByEmailIgnoreCaseOrUsernameIgnoreCase(@NotBlank @Email @Size(max = 345) String email,
                                                                 @NotBlank @Size(max = 60) String username);
}
