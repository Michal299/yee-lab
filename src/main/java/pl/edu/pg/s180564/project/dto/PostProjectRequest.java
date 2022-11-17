package pl.edu.pg.s180564.project.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pg.s180564.project.Project;
import pl.edu.pg.s180564.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostProjectRequest {

    private String projectName;
    private String projectKey;
    private String description;
    private String ownerId;

    public static Project mapRequestToObject(final PostProjectRequest request,
                                             final Function<String, User> userFunction) {
        return Project.builder()
            .projectName(request.projectName)
            .projectKey(request.projectKey)
            .description(request.description)
            .owner(userFunction.apply(request.ownerId))
            .creationDate(LocalDateTime.now())
            .tickets(Set.of())
            .build();
    }
}