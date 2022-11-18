package pl.edu.pg.s180564.config;

import pl.edu.pg.s180564.project.Project;
import pl.edu.pg.s180564.ticket.PriorityType;
import pl.edu.pg.s180564.ticket.Ticket;
import pl.edu.pg.s180564.user.entity.User;
import pl.edu.pg.s180564.user.entity.UserRoleType;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Singleton
@Startup
public class Initializer {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    private Pbkdf2PasswordHash pbkdf;

    @Inject
    public Initializer(Pbkdf2PasswordHash pbkdf) {
        this.pbkdf = pbkdf;
    }

    public Initializer() {

    }


    @PostConstruct
    private synchronized void init() {

        var jhalpert = User.builder()
                .nickname("jhalpert")
                .password(pbkdf.generate("pam1234".toCharArray()))
                .mail("jhalpert@office.com")
                .avatar(getResourceAsByteArray("avatars/jhalpert.png"))
                .userRoles(List.of(UserRoleType.USER))
                .build();

        var pbeasly = User.builder()
                .nickname("pbeasly")
                .password(pbkdf.generate("jim4321".toCharArray()))
                .mail("pbeasly@office.com")
                .avatar(getResourceAsByteArray("avatars/pbeasly.png"))
                .userRoles(List.of(UserRoleType.USER))
                .build();

        var dschrute = User.builder()
                .nickname("dschrute")
                .password(pbkdf.generate("zaq12wsx3edcv".toCharArray()))
                .mail("dschrute@office.com")
                .avatar(getResourceAsByteArray("avatars/dschrute.png"))
                .userRoles(List.of(UserRoleType.USER))
                .build();

        var mscott = User.builder()
                .nickname("mscott")
                .password(pbkdf.generate("admin".toCharArray()))
                .mail("mscott@office.com")
                .avatar(getResourceAsByteArray("avatars/mscott.png"))
                .userRoles(List.of(UserRoleType.ADMIN, UserRoleType.USER))
                .build();

        em.persist(jhalpert);
        em.persist(pbeasly);
        em.persist(dschrute);
        em.persist(mscott);

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
        em.persist(projectAdministration);
        em.persist(projectSales);

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
        em.persist(firstAdministrationTicket);
        em.persist(secondAdministrationTicket);
        em.persist(firstSalesTicket);
    }

    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
