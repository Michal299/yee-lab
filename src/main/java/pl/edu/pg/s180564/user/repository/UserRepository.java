package pl.edu.pg.s180564.user.repository;

import pl.edu.pg.s180564.repository.Repository;
import pl.edu.pg.s180564.user.entity.User;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Dependent
public class UserRepository implements Repository<User, String> {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> find(String id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select user from User user", User.class).getResultList();
    }

    @Override
    public String create(User entity) {
        em.persist(entity);
        return entity.getNickname();
    }

    @Override
    public void delete(User entity) {
        em.remove(find(entity.getNickname()).get());
    }



    @Override
    public void update(User entity) {
        em.merge(entity);
    }

    @Override
    public void detach(User entity) {
        em.detach(entity);
    }
}
