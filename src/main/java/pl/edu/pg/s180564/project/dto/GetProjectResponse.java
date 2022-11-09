package pl.edu.pg.s180564.project.dto;

import lombok.*;
import pl.edu.pg.s180564.project.Project;
import pl.edu.pg.s180564.ticket.Ticket;
import pl.edu.pg.s180564.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetProjectResponse {

    private String projectName;
    private String projectKey;
    private String description;
    private String owner;
    private LocalDateTime creationDate;

    public static GetProjectResponse mapToResponse(final Project project) {
        return GetProjectResponse.builder()
            .projectName(project.getProjectName())
            .projectKey(project.getProjectKey())
            .description(project.getDescription())
            .owner(project.getOwner().getNickname())
            .creationDate(project.getCreationDate())
            .build();
    }
}