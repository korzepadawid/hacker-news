package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;


interface UserRepositoryMongo extends UserRepository, MongoRepository<User, String> {
}
