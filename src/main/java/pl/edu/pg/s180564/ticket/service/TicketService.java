package pl.edu.pg.s180564.ticket.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.s180564.project.service.ProjectService;
import pl.edu.pg.s180564.ticket.Ticket;
import pl.edu.pg.s180564.ticket.repository.TicketRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
@NoArgsConstructor
public class TicketService {

    private TicketRepository ticketRepository;
    private ProjectService projectService;

    @Inject
    public TicketService(final TicketRepository ticketRepository,
                         final ProjectService projectService) {
        this.ticketRepository = ticketRepository;
        this.projectService = projectService;
    }

    public Optional<Ticket> find(String id) {
        return ticketRepository.find(id);
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public String create(Ticket entity) {
        ticketRepository.create(entity);
        projectService.find(entity.getProject().getProjectKey()).ifPresent(project -> project.getTickets().add(entity));
        return entity.getTicketKey();
    }

    public void delete(String entityId) {
        final var ticket = ticketRepository.find(entityId).orElseThrow();
        ticket.getProject().getTickets().remove(ticket);
        ticketRepository.delete(ticketRepository.find(entityId).orElseThrow());
    }

    public void update(Ticket entity) {
        ticketRepository.update(entity);
    }
}
