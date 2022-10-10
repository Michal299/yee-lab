package pl.edu.pg.s180564.project;

import pl.edu.pg.s180564.ticket.Ticket;
import pl.edu.pg.s180564.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class Project {
    private final String projectName;
    private final String projectKey;
    private final String description;
    private final User owner;
    private final LocalDateTime creationDate;
    private final List<Ticket> tickets;
}
