package pl.edu.pg.s180564.project.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pg.s180564.project.Project;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ProjectModel {

    private String projectKey;
    private String projectName;
    private String projectDescription;
    // User nickname
    private String user;
    private String creationDate;
    private List<Ticket> tickets;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Ticket {
        private String ticketKey;
        private String ticketSummary;
    }

    public static ProjectModel mapEntityToModel(final Project project) {
        final var tickets = project.getTickets().stream()
                .map(ticket -> Ticket.builder()
                        .ticketKey(ticket.getTicketKey())
                        .ticketSummary(ticket.getSummary())
                        .build())
                .collect(Collectors.toList());

        return ProjectModel.builder()
                .projectKey(project.getProjectKey())
                .projectName(project.getProjectName())
                .projectDescription(project.getDescription())
                .tickets(tickets)
                .creationDate(project.getCreationDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
                .user(project.getOwner().getNickname())
                .build();
    }
}
