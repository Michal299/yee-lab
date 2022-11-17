package pl.edu.pg.s180564.project.repository;

import pl.edu.pg.s180564.project.Project;
import pl.edu.pg.s180564.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Dependent
public class ProjectRepository implements Repository<Project, String> {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Project> find(String id) {
        return Optional.ofNullable(em.find(Project.class, id));
    }

    @Override
    public List<Project> findAll() {
        return em.createQuery("select project from Project project", Project.class).getResultList();
    }

    @Override
    public String create(Project entity) {
        em.persist(entity);
        return entity.getProjectKey();
    }

    @Override
    public void delete(Project entity) {
        em.remove(find(entity.getProjectKey()).get());
    }

    @Override
    public void update(Project entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Project entity) {
        em.detach(entity);
    }
}
