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


    public static ProjectModel mapEntityToModel(final Project project) {
        return ProjectModel.builder()
                .projectKey(project.getProjectKey())
                .projectName(project.getProjectName())
                .build();
    }
}
