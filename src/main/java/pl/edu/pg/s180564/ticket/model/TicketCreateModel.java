package pl.edu.pg.s180564.ticket.model;

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
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class TicketCreateModel {
    private String ticketKey;
    private String summary;
    private String description;
    private ProjectModel project;
    private PriorityModel priority;
    private UserModel creator;
    private UserModel assignee;

    public static Ticket mapModelToEntity(TicketCreateModel model,
                                          Function<String, Project> projectFunction,
                                          Function<String, PriorityType> priorityTypeFunction,
                                          Function<String, User> userFunction) {
        return Ticket.builder()
                .ticketKey(model.ticketKey)
                .summary(model.summary)
                .description(model.description)
                .project(projectFunction.apply(model.project.getProjectKey()))
                .priorityType(priorityTypeFunction.apply(model.priority.getPriority()))
                .creator(userFunction.apply(model.creator.getNickname()))
                .assignee(userFunction.apply(model.assignee.getNickname()))
                .creationTime(LocalDateTime.now())
                .build();
    }

}
