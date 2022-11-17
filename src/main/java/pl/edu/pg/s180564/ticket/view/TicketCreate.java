package pl.edu.pg.s180564.ticket.view;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.s180564.project.service.ProjectService;
import pl.edu.pg.s180564.ticket.PriorityType;
import pl.edu.pg.s180564.ticket.model.PriorityModel;
import pl.edu.pg.s180564.ticket.model.ProjectModel;
import pl.edu.pg.s180564.ticket.model.TicketCreateModel;
import pl.edu.pg.s180564.ticket.model.UserModel;
import pl.edu.pg.s180564.ticket.service.TicketService;
import pl.edu.pg.s180564.user.service.UserService;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Named
@ConversationScoped
@Log
@NoArgsConstructor
public class TicketCreate implements Serializable {

    private TicketService ticketService;
    private UserService userService;
    private ProjectService projectService;

    @Getter
    private TicketCreateModel ticket;

    @Getter
    private List<ProjectModel> projects;

    @Getter
    private List<UserModel> users;

    @Getter
    private List<PriorityModel> priorities;

    private Conversation conversation;

    @Inject
    public TicketCreate(Conversation conversation) {
        this.conversation = conversation;
    }

    @EJB
    public void setTicketService(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @EJB
    public void setUserService(final UserService userService) {
        this.userService = userService;
    }

    @EJB
    public void setProjectService(final ProjectService projectService) {
        this.projectService = projectService;
    }

    public void init() {
        projects = projectService.findAll().stream()
                .map(ProjectModel::mapEntityToModel)
                .collect(Collectors.toList());
        users = userService.findAll().stream()
                .map(UserModel::mapEntityToModel)
                .collect(Collectors.toList());
        priorities = Arrays.stream(PriorityType.values())
                .map(PriorityModel::mapEntityToModel)
                .collect(Collectors.toList());

        this.ticket = TicketCreateModel.builder().build();
        conversation.begin();
    }

    public String saveAction() throws IOException {
        if (ticketService.findAll().stream()
                .anyMatch(t -> t.getTicketKey().toLowerCase(Locale.ROOT).equals(ticket.getTicketKey().toLowerCase(Locale.ROOT)))) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_CONFLICT, "Project with given key already exist.");
        } else {
            ticketService.create(TicketCreateModel.mapModelToEntity(
                    ticket,
                    (project) -> projectService.find(project).orElseThrow(),
                    (priority) -> Arrays.stream(PriorityType.values()).filter(p -> p.name().equals(priority)).findFirst().orElseThrow(),
                    (user) -> userService.find(user).orElseThrow()));
            conversation.end();
            return "/project/project_view.xhtml?faces-redirect=true&projectKey=" + ticket.getProject().getProjectKey();
        }
        return null;
    }
}
