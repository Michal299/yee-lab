package pl.edu.pg.s180564.ticket.model;

import lombok.*;
import pl.edu.pg.s180564.ticket.PriorityType;
import pl.edu.pg.s180564.ticket.Ticket;
import pl.edu.pg.s180564.user.entity.User;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class TicketEditModel {
    private String summary;
    private String description;
    private PriorityModel priority;
    private UserModel assignee;

    public static TicketEditModel mapEntityToModel(Ticket ticket) {
        return TicketEditModel.builder()
                .summary(ticket.getSummary())
                .description(ticket.getDescription())
                .priority(PriorityModel.mapEntityToModel(ticket.getPriorityType()))
                .assignee(UserModel.mapEntityToModel(ticket.getAssignee()))
                .build();
    }

    public static Ticket mapModelToEntity(TicketEditModel ticketEdit,
                                          Ticket base,
                                          Function<String, PriorityType> priorityTypeFunction,
                                          Function<String, User> userFunction) {
        base.setSummary(ticketEdit.getSummary());
        base.setDescription(ticketEdit.getDescription());
        base.setPriorityType(priorityTypeFunction.apply(ticketEdit.priority.getPriority()));
        base.setAssignee(userFunction.apply(ticketEdit.assignee.getNickname()));
        return base;
    }
}
