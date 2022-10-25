package pl.edu.pg.s180564.project.repository;

import pl.edu.pg.s180564.datastore.InMemoryDatastore;
import pl.edu.pg.s180564.project.Project;
import pl.edu.pg.s180564.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class ProjectRepository implements Repository<Project, String> {

    private final InMemoryDatastore datastore;

    @Inject
    public ProjectRepository(final InMemoryDatastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public Optional<Project> find(String id) {
        return datastore.getProject(id);
    }

    @Override
    public List<Project> findAll() {
        return datastore.getAllProjects();
    }

    @Override
    public String create(Project entity) {
        datastore.addProject(entity);
        return entity.getProjectKey();
    }

    @Override
    public void delete(Project entity) {
        datastore.deleteProject(entity.getProjectKey());
    }

    @Override
    public void update(Project entity) {
        datastore.addProject(entity);
    }
}
