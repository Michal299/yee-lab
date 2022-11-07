package pl.edu.pg.s180564.ticket.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pg.s180564.ticket.PriorityType;
import pl.edu.pg.s180564.ticket.Ticket;
import pl.edu.pg.s180564.user.entity.User;

import java.util.Locale;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutTicketRequest {
    private String summary;
    private String description;
    private String assignee;
    private String priorityType;

    public static Ticket mapRequestToObject(final PutTicketRequest ticketRequest,
                                            final Ticket base,
                                            final Function<String, User> userFunction) {

        if (ticketRequest.summary != null) {
            base.setSummary(ticketRequest.summary);
        }
        if (ticketRequest.description != null) {
            base.setDescription(ticketRequest.description);
        }
        if (ticketRequest.assignee != null) {
            base.setAssignee(userFunction.apply(ticketRequest.assignee));
        }
        if (ticketRequest.priorityType != null) {
            base.setPriorityType(PriorityType.valueOf(ticketRequest.priorityType.toUpperCase(Locale.ROOT)));
        }
        return base;
    }
}
