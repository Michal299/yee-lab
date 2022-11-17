package pl.edu.pg.s180564.config;

import pl.edu.pg.s180564.project.Project;
import pl.edu.pg.s180564.project.service.ProjectService;
import pl.edu.pg.s180564.ticket.PriorityType;
import pl.edu.pg.s180564.ticket.Ticket;
import pl.edu.pg.s180564.ticket.service.TicketService;
import pl.edu.pg.s180564.user.entity.User;
import pl.edu.pg.s180564.user.entity.UserRoleType;
import pl.edu.pg.s180564.user.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class Initializer {

    private UserService userService;
    private ProjectService projectService;
    private TicketService ticketService;

    @EJB
    public void setUserService(final UserService userService) {
        this.userService = userService;
    }

    @EJB
    public void setProjectService(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @EJB
    public void setTicketService(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostConstruct
    private synchronized void init() {

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
    }

    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
