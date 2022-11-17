package pl.edu.pg.s180564.ticket.repository;

import pl.edu.pg.s180564.repository.Repository;
import pl.edu.pg.s180564.ticket.Ticket;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class TicketRepository implements Repository<Ticket, String> {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Ticket> find(String id) {
        return Optional.ofNullable(em.find(Ticket.class, id));
    }

    @Override
    public List<Ticket> findAll() {
        return em.createQuery("select ticket from Ticket ticket", Ticket.class).getResultList();
    }

    @Override
    public String create(Ticket entity) {
        em.persist(entity);
//        System.out.println(em.getFlushMode());
//        em.flush();
        return entity.getTicketKey();
    }

    @Override
    public void delete(Ticket entity) {
        em.remove(find(entity.getTicketKey()).get());
    }

    @Override
    public void update(Ticket entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Ticket entity) {
        em.detach(entity);
    }
}
