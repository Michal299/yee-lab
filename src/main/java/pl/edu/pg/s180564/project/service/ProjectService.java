package pl.edu.pg.s180564.project.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.s180564.project.Project;
import pl.edu.pg.s180564.project.repository.ProjectRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
@NoArgsConstructor
public class ProjectService {

    private ProjectRepository projectRepository;

    @Inject
    public ProjectService(final ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Optional<Project> find(String id) {
        return projectRepository.find(id);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public String create(Project entity) {
        projectRepository.create(entity);
        return entity.getProjectKey();
    }

    public void delete(String entityId) {
        final var project = projectRepository.find(entityId).orElseThrow();
        projectRepository.delete(project);
    }

    public void update(Project entity) {
        projectRepository.update(entity);
    }
}
