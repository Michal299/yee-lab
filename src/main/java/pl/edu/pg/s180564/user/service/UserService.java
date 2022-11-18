package pl.edu.pg.s180564.user.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.s180564.user.entity.User;
import pl.edu.pg.s180564.user.entity.UserRoleType;
import pl.edu.pg.s180564.user.repository.UserRepository;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
@NoArgsConstructor
public class UserService {

    private UserRepository userRepository;

    @Resource(name = "avatars.localization")
    private String avatarsLocation;

    @Inject
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> find(String id) {
        return userRepository.find(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public String create(User entity) {

        // TODO cover authentication
        entity.setUserRoles(List.of(UserRoleType.USER));

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
        final var updatedUser = User.builder()
                .nickname(entity.getNickname())
                .password(entity.getPassword())
                .mail(entity.getMail())
                .avatar(new byte[0])
                .userRoles(entity.getUserRoles())
                .build();

        userRepository.update(updatedUser);
    }

    public void updateAvatar(final String userId, final InputStream avatarInputStream) {
        userRepository.find(userId).ifPresent(user -> {
            try {
                final var newAvatar = avatarInputStream.readAllBytes();
                saveAvatar(user.getNickname(), avatarInputStream);
                final var updated = User.builder()
                        .nickname(user.getNickname())
                        .password(user.getPassword())
                        .mail(user.getMail())
                        .avatar(newAvatar)
                        .userRoles(user.getUserRoles())
                        .build();
                userRepository.update(updated);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void saveAvatar(final String userId, final InputStream avatarInputStream) {
        final var path = Path.of(avatarsLocation, userId + ".png");
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
