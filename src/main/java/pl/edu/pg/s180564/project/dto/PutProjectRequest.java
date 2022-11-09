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

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutProjectRequest {
    private String projectName;
    private String description;
    private String ownerKey;

    public static Project mapRequestToObject(final PutProjectRequest request,
                                          final Project base,
                                          final Function<String, User> userFunction) {
        if (request.description != null) {
            base.setDescription(request.description);
        }
        if (request.projectName != null) {
            base.setProjectName(request.projectName);
        }
        if (request.ownerKey != null) {
            base.setOwner(userFunction.apply(request.ownerKey));
        }
        return base;
    }
}