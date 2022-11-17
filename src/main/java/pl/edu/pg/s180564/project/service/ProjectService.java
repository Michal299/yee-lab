package pl.edu.pg.s180564.project.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.s180564.project.Project;
import pl.edu.pg.s180564.project.repository.ProjectRepository;
import pl.edu.pg.s180564.ticket.service.TicketService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class ProjectService {

    private ProjectRepository projectRepository;
    private TicketService ticketService;

    @Inject
    public ProjectService(final ProjectRepository projectRepository,
                          final TicketService ticketService) {
        this.projectRepository = projectRepository;
        this.ticketService = ticketService;
    }

    public Optional<Project> find(String id) {
        return projectRepository.find(id);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Transactional
    public String create(Project entity) {
        projectRepository.create(entity);
        return entity.getProjectKey();
    }

    @Transactional
    public void delete(String entityId) {
        final var project = projectRepository.find(entityId).orElseThrow();
        projectRepository.delete(project);
    }

    @Transactional
    public void update(Project entity) {
        projectRepository.update(entity);
    }
}
