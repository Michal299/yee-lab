package pl.edu.pg.s180564.ticket.repository;

import pl.edu.pg.s180564.datastore.InMemoryDatastore;
import pl.edu.pg.s180564.repository.Repository;
import pl.edu.pg.s180564.ticket.Ticket;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class TicketRepository implements Repository<Ticket, String> {

    private final InMemoryDatastore datastore;

    @Inject
    public TicketRepository(final InMemoryDatastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public Optional<Ticket> find(String id) {
        return datastore.getTicket(id);
    }

    @Override
    public List<Ticket> findAll() {
        return datastore.getAllTickets();
    }

    @Override
    public String create(Ticket entity) {
        datastore.addTicket(entity);
        return entity.getTicketKey();
    }

    @Override
    public void delete(Ticket entity) {
        datastore.deleteTicket(entity.getTicketKey());
    }

    @Override
    public void update(Ticket entity) {
        datastore.addTicket(entity);
    }
}
