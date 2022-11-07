package pl.edu.pg.s180564.ticket.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.s180564.ticket.Ticket;
import pl.edu.pg.s180564.ticket.repository.TicketRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class TicketService {

    private TicketRepository ticketRepository;

    @Inject
    public TicketService(final TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Optional<Ticket> find(String id) {
        return ticketRepository.find(id);
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public String create(Ticket entity) {
        ticketRepository.create(entity);
        return entity.getTicketKey();
    }

    public void delete(String entityId) {
        ticketRepository.delete(ticketRepository.find(entityId).orElseThrow());
    }

    public void update(Ticket entity) {
        ticketRepository.update(entity);
    }
}
