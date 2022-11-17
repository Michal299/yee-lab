package pl.edu.pg.s180564.config;

import pl.edu.pg.s180564.project.Project;
import pl.edu.pg.s180564.project.service.ProjectService;
import pl.edu.pg.s180564.ticket.PriorityType;
import pl.edu.pg.s180564.ticket.Ticket;
import pl.edu.pg.s180564.ticket.service.TicketService;
import pl.edu.pg.s180564.user.entity.User;
import pl.edu.pg.s180564.user.entity.UserRoleType;
import pl.edu.pg.s180564.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.control.RequestContextController;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@ApplicationScoped
public final class Initializer {

    private final UserService userService;
    private final ProjectService projectService;
    private final TicketService ticketService;
    private final RequestContextController requestContextController;

    @Inject
    public Initializer(final UserService userService,
                       final ProjectService projectService,
                       final TicketService ticketService,
                       final RequestContextController requestContextController) {
        this.userService = userService;
        this.projectService = projectService;
        this.ticketService = ticketService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init() {
        requestContextController.activate();

        var jhalpert = User.builder()
                .nickname("jhalpert")
                .password("pam1234")
                .mail("jhalpert@office.com")
                .avatar(getResourceAsByteArray("avatars/jhalpert.png"))
                .userRole(UserRoleType.USER)
                .build();

        var pbeasly = User.builder()
                .nickname("pbeasly")
                .password("jim4321")
                .mail("pbeasly@office.com")
                .avatar(getResourceAsByteArray("avatars/pbeasly.png"))
                .userRole(UserRoleType.USER)
                .build();

        var dschrute = User.builder()
                .nickname("dschrute")
                .password("zaq12wsx3edcv")
                .mail("dschrute@office.com")
                .avatar(getResourceAsByteArray("avatars/dschrute.png"))
                .userRole(UserRoleType.USER)
                .build();

        var mscott = User.builder()
                .nickname("mscott")
                .password("admin")
                .mail("mscott@office.com")
                .avatar(getResourceAsByteArray("avatars/mscott.png"))
                .userRole(UserRoleType.ADMIN)
                .build();

        userService.create(jhalpert);
        userService.create(pbeasly);
        userService.create(dschrute);
        userService.create(mscott);

        var projectAdministration = Project.builder()
                .projectKey("ADM")
                .projectName("Administration")
                .description("Administration project to track office equipment delivers and orders.")
                .owner(mscott)
                .creationDate(LocalDateTime.now())
                .build();
        var projectSales = Project.builder()
                .projectKey("SL")
                .projectName("Sales")
                .description("Sales related stuff.")
                .owner(dschrute)
                .creationDate(LocalDateTime.now())
                .build();
        projectService.create(projectAdministration);
        projectService.create(projectSales);

        var firstAdministrationTicket = Ticket.builder()
                .ticketKey("ADM-1")
                .summary("New pencils")
                .description("Buy two boxes of new pencils")
                .creator(dschrute)
                .assignee(pbeasly)
                .creationTime(LocalDateTime.now())
                .project(projectAdministration)
                .priorityType(PriorityType.MEDIUM)
                .build();
        var secondAdministrationTicket = Ticket.builder()
                .ticketKey("ADM-2")
                .summary("New tapes")
                .description("Buy three boxes of new tapes")
                .creator(mscott)
                .assignee(pbeasly)
                .creationTime(LocalDateTime.now())
                .project(projectAdministration)
                .priorityType(PriorityType.LOW)
                .build();
        var firstSalesTicket = Ticket.builder()
                .ticketKey("SL-1")
                .summary("Promote Dwight")
                .description("Promote Dwight to regional manager of the Scranton, Pennsylvania branch of Dudner Mifflin Inc.")
                .creator(mscott)
                .assignee(mscott)
                .creationTime(LocalDateTime.now())
                .project(projectSales)
                .priorityType(PriorityType.HIGH)
                .build();
        ticketService.create(firstAdministrationTicket);
        ticketService.create(secondAdministrationTicket);
        ticketService.create(firstSalesTicket);
        requestContextController.deactivate();
    }

    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
