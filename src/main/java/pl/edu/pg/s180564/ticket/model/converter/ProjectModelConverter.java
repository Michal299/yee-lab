package pl.edu.pg.s180564.ticket.model.converter;

import pl.edu.pg.s180564.project.service.ProjectService;
import pl.edu.pg.s180564.ticket.model.ProjectModel;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = ProjectModel.class, managed = true)
public class ProjectModelConverter implements Converter<ProjectModel> {

    private ProjectService projectService;

    @Inject
    public ProjectModelConverter(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public ProjectModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        final var maybeProject = projectService.find(value);
        return maybeProject.map(ProjectModel::mapEntityToModel).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, ProjectModel value) {
        return value == null
                ? ""
                : value.getProjectKey();
    }
}
