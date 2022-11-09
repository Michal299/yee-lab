package pl.edu.pg.s180564.ticket.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pg.s180564.project.Project;
import pl.edu.pg.s180564.ticket.PriorityType;
import pl.edu.pg.s180564.ticket.Ticket;
import pl.edu.pg.s180564.user.entity.User;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostTicketRequest {

    private String ticketKey;
    private String summary;
    private String description;
    private String creatorNickname;
    private String assigneeNickname;
    private String priorityType;

    public static Ticket mapRequestToObject(final PostTicketRequest request,
                                            final Function<String, User> userFunction,
                                            final Supplier<Project> projectSupplier) {
        return Ticket.builder()
            .ticketKey(request.ticketKey)
            .summary(request.summary)
            .description(request.description)
            .creator(userFunction.apply(request.creatorNickname))
            .assignee(userFunction.apply(request.assigneeNickname))
            .creationTime(LocalDateTime.now())
            .project(projectSupplier.get())
            .priorityType(PriorityType.valueOf(request.priorityType.toUpperCase(Locale.ROOT)))
            .build();
    }
}
