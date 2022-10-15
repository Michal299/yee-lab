package pl.edu.pg.s180564.config;

import pl.edu.pg.s180564.user.entity.User;
import pl.edu.pg.s180564.user.entity.UserRoleType;
import pl.edu.pg.s180564.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@ApplicationScoped
public final class Initializer {

    private final UserService userService;

    @Inject
    public Initializer(final UserService userService) {
        this.userService = userService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init() {
        final List<User> users = List.of(
                new User("jhalpert",
                        "pam1234",
                        "jhalpert@office.com",
                        getResourceAsByteArray("avatars/jhalpert.png"),
                        UserRoleType.USER),

                new User("dschrute",
                        "zaq12wsx3edcv",
                        "dschrute@office.com",
                        getResourceAsByteArray("avatars/pbeasly.png"),
                        UserRoleType.USER),
                new User("dschrute",
                        "zaq12wsx3edcv",
                        "dschrute@office.com",
                        getResourceAsByteArray("avatars/dschrute.png"),
                        UserRoleType.USER),

                new User("mscott",
                        "admin",
                        "mscott@office.com",
                        getResourceAsByteArray("avatars/mscott.png"),
                        UserRoleType.ADMIN)
        );

        users.forEach(userService::create);
    }

    private byte[] getResourceAsByteArray(String name) {
        System.out.println("Reading: " + name);
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
