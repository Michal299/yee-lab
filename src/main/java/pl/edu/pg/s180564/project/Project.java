package pl.edu.pg.s180564.project;

import pl.edu.pg.s180564.ticket.Ticket;
import pl.edu.pg.s180564.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Project {
    private final String projectName;
    private final String projectKey;
    private final String description;
    private final User owner;
    private final LocalDateTime creationDate;
    private final List<Ticket> tickets;

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

    public String getProjectName() {
        return projectName;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public String getDescription() {
        return description;
    }

    public User getOwner() {
        return owner;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<Ticket> getTickets() {
        return tickets;
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
