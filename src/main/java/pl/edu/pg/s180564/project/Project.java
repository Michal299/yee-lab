package pl.edu.pg.s180564.project;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.s180564.ticket.Ticket;
import pl.edu.pg.s180564.user.entity.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class Project implements Serializable {
    private String projectName;
    private String projectKey;
    private String description;
    private User owner;
    private LocalDateTime creationDate;
    private List<Ticket> tickets;

    public Project(final String projectName,
                   final String projectKey,
                   final String description,
                   final User owner,
                   final LocalDateTime creationDate,
                   final List<Ticket> tickets) {
        this.projectName = projectName;
        this.projectKey = projectKey;
        this.description = description;
        this.owner = owner;
        this.creationDate = creationDate;
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(projectName, project.projectName)
                && Objects.equals(projectKey, project.projectKey)
                && Objects.equals(description, project.description)
                && Objects.equals(owner, project.owner)
                && Objects.equals(creationDate, project.creationDate)
                && Objects.equals(tickets, project.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectName,
                projectKey,
                description,
                owner,
                creationDate,
                tickets
        );
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", projectKey='" + projectKey + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + owner +
                ", creationDate=" + creationDate +
                ", tickets=" + tickets +
                '}';
    }
}
