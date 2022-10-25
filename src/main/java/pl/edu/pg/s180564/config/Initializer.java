package pl.edu.pg.s180564.config;

import pl.edu.pg.s180564.project.Project;
import pl.edu.pg.s180564.project.model.ProjectModel;
import pl.edu.pg.s180564.project.service.ProjectService;
import pl.edu.pg.s180564.ticket.PriorityType;
import pl.edu.pg.s180564.ticket.Ticket;
import pl.edu.pg.s180564.ticket.service.TicketService;
import pl.edu.pg.s180564.user.entity.User;
import pl.edu.pg.s180564.user.entity.UserRoleType;
import pl.edu.pg.s180564.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public final class Initializer {

    private final UserService userService;
    private final ProjectService projectService;
    private final TicketService ticketService;

    @Inject
    public Initializer(final UserService userService,
                       final ProjectService projectService,
                       final TicketService ticketService) {
        this.userService = userService;
        this.projectService = projectService;
        this.ticketService = ticketService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init() {
        initializeMockUsers();
        initializeMockProjects();
        initializeTickets();
    }

    private void initializeMockUsers() {
        final List<User> users = List.of(
                new User("jhalpert",
                        "pam1234",
                        "jhalpert@office.com",
                        getResourceAsByteArray("avatars/jhalpert.png"),
                        UserRoleType.USER),

                new User("pbeasly",
                        "jim4321",
                        "pbeasly@office.com",
                        getResourceAsByteArray("avatars/pbeasly.png"),
                        UserRoleType.USER),

                new User("dschrute",
                        "zaq12wsx3edcv",
                        "dschrute@office.com",
                        getResourceAsByteArray("avatars/dschrute.png"),
                        UserRoleType.USER),

                new User("mscott",
                        "admin",
                        "mscott@office.com",
                        getResourceAsByteArray("avatars/mscott.png"),
                        UserRoleType.ADMIN)
        );

        users.forEach(userService::create);
    }

    private void initializeMockProjects() {
        final List<Project> projects = List.of(
                new Project("Administration",
                        "ADM",
                        "Administration project to track office equipment delivers and orders.",
                        userService.find("mscott").orElseThrow(),
                        LocalDateTime.now(),
                        new ArrayList<>()),
                new Project("Sales",
                        "SL",
                        "Sales related stuff.",
                        userService.find("dschrute").orElseThrow(),
                        LocalDateTime.now(),
                        new ArrayList<>())
        );
        projects.forEach(projectService::create);
    }

    private void initializeTickets() {
        final var administrationProject = projectService.find("ADM").orElseThrow();
        final var salesProject = projectService.find("SL").orElseThrow();
        final List<Ticket> tickets = List.of(
                new Ticket("ADM-1",
                        "New pencils",
                        "Buy two boxes of new pencils",
                        userService.find("dschrute").orElseThrow(),
                        userService.find("pbeasly").orElseThrow(),
                        LocalDateTime.now(),
                        administrationProject,
                        PriorityType.MEDIUM),
                new Ticket("ADM-2",
                        "New tapes",
                        "Buy three boxes of new tapes",
                        userService.find("mscott").orElseThrow(),
                        userService.find("pbeasly").orElseThrow(),
                        LocalDateTime.now(),
                        administrationProject,
                        PriorityType.LOW),
                new Ticket("SL-1",
                        "Promote Dwight",
                        "Promote Dwight to regional manager of the Scranton, Pennsylvania branch of Dudner Mifflin Inc.",
                        userService.find("mscott").orElseThrow(),
                        userService.find("mscott").orElseThrow(),
                        LocalDateTime.now(),
                        salesProject,
                        PriorityType.HIGH)
        );
        tickets.forEach(ticketService::create);
    }

    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
