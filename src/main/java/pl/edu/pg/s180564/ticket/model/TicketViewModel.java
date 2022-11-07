package pl.edu.pg.s180564.ticket.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pg.s180564.ticket.Ticket;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class TicketViewModel {
    private String ticketKey;
    private String summary;
    private String description;
    // nicknames
    private String creator;
    private String assignee;
    private String creationTime;
    // url to project
    private String project;
    private String priority;

    public static TicketViewModel mapEntityToModel(final Ticket ticket) {
        return TicketViewModel.builder()
                .ticketKey(ticket.getTicketKey())
                .summary(ticket.getSummary())
                .description(ticket.getDescription())
                .creator(ticket.getCreator().getNickname())
                .assignee(ticket.getAssignee().getNickname())
                .creationTime(ticket.getCreationTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
                .project(ticket.getProject().getProjectKey())
                .priority(ticket.getPriorityType().name())
                .build();
    }
}
