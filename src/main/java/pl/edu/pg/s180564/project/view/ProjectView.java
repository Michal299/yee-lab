package pl.edu.pg.s180564.project.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.s180564.project.model.ProjectModel;
import pl.edu.pg.s180564.project.service.ProjectService;
import pl.edu.pg.s180564.ticket.service.TicketService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@SessionScoped
@Named
public class ProjectView implements Serializable {

    private ProjectService projectService;
    private TicketService ticketService;

    @Getter
    @Setter
    private String projectKey;

    @Getter
    private ProjectModel project;

    public ProjectView() {
    }

    @EJB
    public void setProjectService(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @EJB
    public void setTicketService(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public void init() throws IOException {
        final var maybeProject = projectService.find(projectKey);
        if (maybeProject.isPresent()) {
            project = ProjectModel.mapEntityToModel(maybeProject.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Project with given key not found.");
        }
    }

    public String deleteAction(ProjectModel.Ticket ticket) {
        ticketService.delete(ticket.getTicketKey());
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
