package ittep.restapidemo.repository;

import ittep.restapidemo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByLogin(String name);

    List<User> findAllByProvider(String provider);

    Optional<User> findByLoginAndProvider(String login, String provider);

    Boolean existsByLoginAndProvider(String login, String provider);
}
