package pl.edu.pg.s180564.user.repository;

import pl.edu.pg.s180564.datastore.InMemoryDatastore;
import pl.edu.pg.s180564.repository.Repository;
import pl.edu.pg.s180564.user.entity.User;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class UserRepository implements Repository<User, String> {

    private final InMemoryDatastore datastore;

    @Inject
    public UserRepository(final InMemoryDatastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public Optional<User> find(String id) {
        return datastore.getUser(id);
    }

    @Override
    public List<User> findAll() {
        return datastore.getAllUsers();
    }

    @Override
    public String create(User entity) {
        datastore.addUser(entity);
        return entity.getNickname();
    }

    @Override
    public void delete(User entity) {
        datastore.deleteUser(entity.getNickname());
    }

    @Override
    public void update(User entity) {
        datastore.addUser(entity);
    }
}
