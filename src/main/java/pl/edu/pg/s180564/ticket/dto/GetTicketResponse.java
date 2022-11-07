package pl.edu.pg.s180564.ticket.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pg.s180564.ticket.Ticket;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetTicketResponse {
    private String ticketKey;
    private String summary;
    private String description;
    private String creatorNickname;
    private String assigneeNickname;
    private LocalDateTime creationTime;
    private String project;
    private String priorityType;

    public static GetTicketResponse mapToResponse(final Ticket ticket) {
        return GetTicketResponse.builder()
        .ticketKey(ticket.getTicketKey())
        .summary(ticket.getSummary())
        .description(ticket.getDescription())
        .creatorNickname(ticket.getCreator().getNickname())
        .assigneeNickname(ticket.getAssignee().getNickname())
        .creationTime(ticket.getCreationTime())
        .project(ticket.getProject().getProjectKey())
        .priorityType(ticket.getPriorityType().name())
        .build();
    }
}