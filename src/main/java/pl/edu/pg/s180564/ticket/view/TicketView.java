package pl.edu.pg.s180564.ticket.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.s180564.ticket.model.TicketViewModel;
import pl.edu.pg.s180564.ticket.service.TicketService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Named
@RequestScoped
public class TicketView {

    private final TicketService ticketService;

    @Getter
    @Setter
    private String ticketKey;

    @Getter
    private TicketViewModel ticket;

    @Inject
    public TicketView(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public void init() throws IOException {
        final var maybeTicket = ticketService.find(ticketKey);
        if (maybeTicket.isEmpty()) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND,
                            "Ticket with given key doesn't exist."
                    );
            return;
        }

        ticket = TicketViewModel.mapEntityToModel(maybeTicket.get());
    }
}
