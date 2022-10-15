package pl.edu.pg.s180564.datastore;

import pl.edu.pg.s180564.user.entity.User;
import pl.edu.pg.s180564.utils.CloningUtil;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class InMemoryDatastore {

    private final HashMap<String, User> userStore = new HashMap<>();

    public void addUser(User user) {
        if (userStore.containsKey(user.getNickname())) {
            userStore.replace(user.getNickname(), user);
        } else {
            userStore.put(user.getNickname(), user);
        }
    }

    public Optional<User> getUser(String userId) {
        if (userStore.containsKey(userId)) {
            return Optional.of(CloningUtil.clone(userStore.get(userId)));
        } else {
            return Optional.empty();
        }
    }

    public List<User> getAllUsers() {
        return userStore.values().stream()
                .map(CloningUtil::clone)
                .collect(Collectors.toList());
    }

    public void deleteUser(String userId) {
        userStore.remove(userId);
    }
}
