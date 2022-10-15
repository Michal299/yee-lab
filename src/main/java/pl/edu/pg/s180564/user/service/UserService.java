package pl.edu.pg.s180564.user.service;

import pl.edu.pg.s180564.user.entity.User;
import pl.edu.pg.s180564.user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    private UserRepository userRepository;

    @Inject
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserService() {

    }

    public Optional<User> find(String id) {
        return userRepository.find(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public String create(User entity) {
        userRepository.create(entity);
        return entity.getNickname();
    }

    public void delete(User entity) {
        userRepository.delete(entity);
    }

    public void update(User entity) {
        userRepository.update(entity);
    }
}
