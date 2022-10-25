package pl.edu.pg.s180564.project.view;

import pl.edu.pg.s180564.project.model.ProjectsListModel;
import pl.edu.pg.s180564.project.service.ProjectService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
public class ProjectList implements Serializable {

    private final ProjectService projectService;

    private ProjectsListModel projects;

    @Inject
    public ProjectList(final ProjectService projectService) {
        this.projectService = projectService;
    }

    public ProjectsListModel getProjects() {
        if (projects == null) {
            projects = ProjectsListModel.mapEntityToModel(projectService.findAll());
        }
        return projects;
    }

    public String deleteAction(ProjectsListModel.Project project) {
        projectService.delete(project.getProjectKey());
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
