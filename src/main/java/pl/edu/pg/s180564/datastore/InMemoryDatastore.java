package pl.edu.pg.s180564.datastore;

import pl.edu.pg.s180564.project.Project;
import pl.edu.pg.s180564.ticket.Ticket;
import pl.edu.pg.s180564.user.entity.User;
import pl.edu.pg.s180564.utils.CloningUtil;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class InMemoryDatastore {

    private final HashMap<String, User> userStore = new HashMap<>();
    private final HashMap<String, Project> projectStore = new HashMap<>();
    private final HashMap<String, Ticket> ticketStore = new HashMap<>();

    public void addUser(User user) {
        if (userStore.containsKey(user.getNickname())) {
            userStore.replace(user.getNickname(), user);
        } else {
            userStore.put(user.getNickname(), user);
        }
    }

    public Optional<User> getUser(String userId) {
        if (userStore.containsKey(userId)) {
            return Optional.of(CloningUtil.clone(userStore.get(userId)));
        } else {
            return Optional.empty();
        }
    }

    public List<User> getAllUsers() {
        return userStore.values().stream()
                .map(CloningUtil::clone)
                .collect(Collectors.toList());
    }

    public void deleteUser(String userId) {
        userStore.remove(userId);
    }

    public void addProject(Project project) {
        if (projectStore.containsKey(project.getProjectKey())) {
            projectStore.replace(project.getProjectKey(), project);
        } else {
            projectStore.put(project.getProjectKey(), project);
        }
    }

    public Optional<Project> getProject(String projectId) {
        if (projectStore.containsKey(projectId)) {
            final var project = CloningUtil.clone(projectStore.get(projectId));
            project.setTickets(getAllTickets()
                    .stream()
                    .filter(ticket -> ticket.getProject().getProjectKey().equals(project.getProjectKey()))
                    .collect(Collectors.toList()));
            return Optional.of(project);
        } else {
            return Optional.empty();
        }
    }

    public List<Project> getAllProjects() {
        final var projects = projectStore.values().stream()
                .map(CloningUtil::clone)
                .collect(Collectors.toList());
        
        projects.forEach(project -> project.setTickets(getAllTickets().stream()
                .filter(ticket -> ticket.getProject().getProjectKey().equals(project.getProjectKey()))
                .collect(Collectors.toList())));
        return projects;
    }

    public void deleteProject(String projectId) {
        projectStore.remove(projectId);
    }

    public void addTicket(Ticket ticket) {
        if (ticketStore.containsKey(ticket.getTicketKey())) {
            ticketStore.replace(ticket.getTicketKey(), ticket);
        } else {
            ticketStore.put(ticket.getTicketKey(), ticket);
        }
    }

    public Optional<Ticket> getTicket(String ticketId) {
        if (ticketStore.containsKey(ticketId)) {
            return Optional.of(CloningUtil.clone(ticketStore.get(ticketId)));
        } else {
            return Optional.empty();
        }
    }

    public List<Ticket> getAllTickets() {
        return ticketStore.values().stream()
                .map(CloningUtil::clone)
                .collect(Collectors.toList());
    }

    public void deleteTicket(String ticketId) {
        ticketStore.remove(ticketId);
    }
}
