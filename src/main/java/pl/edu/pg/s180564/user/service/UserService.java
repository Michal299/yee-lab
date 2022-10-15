package pl.edu.pg.s180564.user.service;

import pl.edu.pg.s180564.user.entity.User;
import pl.edu.pg.s180564.user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
        final var avatar = entity.getAvatar();
        if (avatar.length > 0) {
            saveAvatar(entity.getNickname(), new ByteArrayInputStream(avatar));
        }

        return entity.getNickname();
    }

    public void delete(User entity) {
        userRepository.delete(entity);
    }

    public void update(User entity) {
        userRepository.update(entity);
    }

    public void deleteAvatar(User entity) {
        final var updatedUser = new User(entity.getNickname(),
                entity.getPassword(),
                entity.getMail(),
                new byte[0],
                entity.getUserRole()
        );

        userRepository.update(updatedUser);
    }

    public void updateAvatar(final String userId, final InputStream avatarInputStream) {
        userRepository.find(userId).ifPresent(user -> {
            try {
                final var newAvatar = avatarInputStream.readAllBytes();
                saveAvatar(user.getNickname(), avatarInputStream);
                final var updated = new User(user.getNickname(), user.getPassword(), user.getMail(), newAvatar, user.getUserRole());
                userRepository.update(updated);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void saveAvatar(final String userId, final InputStream avatarInputStream) {
        final var path = Paths.get(userId + ".png");
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            Files.write(path, avatarInputStream.readAllBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
