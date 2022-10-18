package pl.edu.pg.s180564.ticket.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.s180564.ticket.PriorityType;
import pl.edu.pg.s180564.ticket.model.PriorityModel;
import pl.edu.pg.s180564.ticket.model.TicketEditModel;
import pl.edu.pg.s180564.ticket.model.UserModel;
import pl.edu.pg.s180564.ticket.service.TicketService;
import pl.edu.pg.s180564.user.service.UserService;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class TicketEdit implements Serializable {
    private final TicketService ticketService;
    private final UserService userService;

    @Getter
    @Setter
    private String ticketKey;

    @Getter
    private List<UserModel> users;

    @Getter
    private List<PriorityModel> priorities;

    @Getter
    private TicketEditModel ticket;

    @Inject
    public TicketEdit(final TicketService ticketService,
                      final UserService userService) {
        this.ticketService = ticketService;
        this.userService = userService;
    }

    public void init() throws IOException {
        final var maybeTicket = ticketService.find(ticketKey);
        if (maybeTicket.isEmpty()) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Ticket not found");
            return;
        }

        users = userService.findAll().stream()
                .map(UserModel::mapEntityToModel)
                .collect(Collectors.toList());
        priorities = Arrays.stream(PriorityType.values())
                .map(PriorityModel::mapEntityToModel)
                .collect(Collectors.toList());
        ticket = TicketEditModel.mapEntityToModel(maybeTicket.get());
    }

    public String saveAction() {
        ticketService.update(TicketEditModel.mapModelToEntity(
                ticket,
                ticketService.find(ticketKey).orElseThrow(),
                (priority) -> Arrays.stream(PriorityType.values())
                        .filter(p -> p.name().toLowerCase(Locale.ROOT).equals(priority.toLowerCase(Locale.ROOT)))
                        .findFirst()
                        .orElseThrow(),
                (user) -> userService.find(user).orElseThrow()
        ));
        return "ticket_view.xhtml?faces-redirect=true&includeViewParams=true&ticketKey=" + ticketKey;
    }
}
