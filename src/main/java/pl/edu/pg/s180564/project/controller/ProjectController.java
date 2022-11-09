package pl.edu.pg.s180564.project.controller;

import pl.edu.pg.s180564.project.dto.GetProjectResponse;
import pl.edu.pg.s180564.project.dto.GetProjectsResponse;
import pl.edu.pg.s180564.project.dto.PostProjectRequest;
import pl.edu.pg.s180564.project.service.ProjectService;
import pl.edu.pg.s180564.project.dto.PutProjectRequest;
import pl.edu.pg.s180564.user.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/projects")
public class ProjectController {

    private ProjectService projectService;
    private UserService userService;

    public ProjectController() {

    }

    @Inject
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjects() {
        return Response.ok(GetProjectsResponse.mapToResponse(projectService.findAll())).build();
    }

    @GET
    @Path("{projectKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProject(@PathParam("projectKey") String projectKey) {
        final var maybeProject = projectService.find(projectKey);
        if (maybeProject.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(GetProjectResponse.mapToResponse(maybeProject.get())).build();
    }

    @DELETE
    @Path("{projectKey}")
    public Response deleteProject(@PathParam("projectKey") String projectKey) {
        projectService.delete(projectKey);
        return Response.accepted().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProject(PostProjectRequest projectRequest) {
        final var project = PostProjectRequest.mapRequestToObject(
            projectRequest,
            (id) -> userService.find(id).orElseThrow()
        );
        if (projectService.findAll().stream().anyMatch(p -> p.getProjectKey().equals(project.getProjectKey()))) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        final var createdProjectKey = projectService.create(project);
        return Response.created(
            UriBuilder
                .fromPath("/projects")
                .path(createdProjectKey)
                .build()
        ).build();
    }

    @PUT
    @Path("{projectKey}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProject(@PathParam("projectKey") String projectKey,
                                  PutProjectRequest projectRequest) {
        final var projectToUpdate = projectService.find(projectKey);
        if (projectToUpdate.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final var updatedProject = PutProjectRequest.mapRequestToObject(
            projectRequest,
            projectToUpdate.get(),
            (userNickname) -> userService.find(userNickname).orElseThrow());
        projectService.update(updatedProject);
        return Response.noContent().build();
    }
}
