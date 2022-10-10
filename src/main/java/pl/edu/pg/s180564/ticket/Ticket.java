package pl.edu.pg.s180564.ticket;

import pl.edu.pg.s180564.project.Project;
import pl.edu.pg.s180564.user.User;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket {
    private final String summary;
    private final String description;
    private final User creator;
    private final User assignee;
    private final LocalDateTime creationTime;
    private final Project project;
    private final PriorityType priorityType;

    public Ticket(final String summary,
                  final String description,
                  final User creator,
                  final User assignee,
                  final LocalDateTime creationTime,
                  final Project project,
                  final PriorityType priorityType) {
        this.summary = summary;
        this.description = description;
        this.creator = creator;
        this.assignee = assignee;
        this.creationTime = creationTime;
        this.project = project;
        this.priorityType = priorityType;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public User getCreator() {
        return creator;
    }

    public User getAssignee() {
        return assignee;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public Project getProject() {
        return project;
    }

    public PriorityType getPriorityType() {
        return priorityType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(summary, ticket.summary)
                && Objects.equals(description, ticket.description)
                && Objects.equals(creator, ticket.creator)
                && Objects.equals(assignee, ticket.assignee)
                && Objects.equals(creationTime, ticket.creationTime)
                && Objects.equals(project, ticket.project)
                && Objects.equals(priorityType, ticket.priorityType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(summary,
                description,
                creator,
                assignee,
                creationTime,
                project,
                priorityType);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", creator=" + creator +
                ", assignee=" + assignee +
                ", creationTime=" + creationTime +
                ", project=" + project +
                ", priority=" + priorityType +
                '}';
    }
}
