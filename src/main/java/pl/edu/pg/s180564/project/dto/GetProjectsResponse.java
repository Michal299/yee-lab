package pl.edu.pg.s180564.project.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetProjectsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Project {
        private String projectKey;
        private String projectName;
    }

    @Singular
    private List<Project> projects;

    public static GetProjectsResponse mapToResponse(final List<pl.edu.pg.s180564.project.Project> projects) {
        final var dtoProjectsCollection = projects.stream()
            .map(project -> Project.builder()
                .projectKey(project.getProjectKey())
                .projectName(project.getProjectName())
                .build())
            .collect(Collectors.toList());
        return new GetProjectsResponse(dtoProjectsCollection);
    }
}
