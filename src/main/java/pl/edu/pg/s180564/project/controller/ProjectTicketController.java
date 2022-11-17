package pl.edu.pg.s180564.project.controller;

import pl.edu.pg.s180564.ticket.Ticket;
import pl.edu.pg.s180564.ticket.dto.GetTicketResponse;
import pl.edu.pg.s180564.ticket.dto.GetTicketsResponse;
import pl.edu.pg.s180564.ticket.dto.PostTicketRequest;
import pl.edu.pg.s180564.project.service.ProjectService;
import pl.edu.pg.s180564.ticket.dto.PutTicketRequest;
import pl.edu.pg.s180564.ticket.service.TicketService;
import pl.edu.pg.s180564.user.service.UserService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("projects/{projectKey}/tickets")
public class ProjectTicketController {

    private TicketService ticketService;
    private ProjectService projectService;
    private UserService userService;

    public ProjectTicketController() {

    }

    @EJB
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @EJB
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTicketsForProject(@PathParam("projectKey") String projectKey) {
        final var maybeProject = projectService.find(projectKey);
        if (maybeProject.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final var tickets = new ArrayList<>(maybeProject.get().getTickets());
        return Response.ok(GetTicketsResponse.mapToResponse(tickets)).build();
    }

    @GET
    @Path("{ticketKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTicket(@PathParam("projectKey") String projectKey,
                              @PathParam("ticketKey") String ticketKey) {
        final var maybeProject = projectService.find(projectKey);
        if (maybeProject.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final var maybeTicket = ticketService.find(ticketKey);
        if (maybeTicket.isEmpty() || !maybeTicket.get().getProject().getProjectKey().equals(maybeProject.get().getProjectKey())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(GetTicketResponse.mapToResponse(maybeTicket.get())).build();
    }

    @DELETE
    @Path("{ticketKey}")
    public Response deleteTicket(@PathParam("projectKey") String projectKey,
                                 @PathParam("ticketKey") String ticketKey) {
        final var maybeTicket = getTicketUnderProject(projectKey, ticketKey);
        if (maybeTicket.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        ticketService.delete(maybeTicket.get().getTicketKey());
        return Response.accepted().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTicket(@PathParam("projectKey") String projectKey,
                                 PostTicketRequest request) {
        final var ticket = PostTicketRequest.mapRequestToObject(
            request,
            userId -> userService.find(userId).orElseThrow(),
            () -> projectService.find(projectKey).orElseThrow());
        if (ticketService.findAll().stream()
            .anyMatch(t -> t.getTicketKey().equals(ticket.getTicketKey()))) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        final var createdTicketKey = ticketService.create(ticket);
        return Response.created(
            UriBuilder
                .fromPath("/projects")
                .path(projectKey)
                .path("/tickets")
                .path(createdTicketKey)
                .build()
        ).build();
    }

    @PUT
    @Path("{ticketKey}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTicket(@PathParam("projectKey") String projectKey,
                                 @PathParam("ticketKey") String ticketKey,
                                 PutTicketRequest ticketRequest) {
        final var maybeTicket = getTicketUnderProject(projectKey, ticketKey);
        if (maybeTicket.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final var updated = PutTicketRequest.mapRequestToObject(
            ticketRequest,
            maybeTicket.get(),
            (userNickname) -> userService.find(userNickname).orElseThrow());
        ticketService.update(updated);
        return Response.noContent().build();
    }


    private Optional<Ticket> getTicketUnderProject(String projectKey, String ticketKey) {
        final var maybeProject = projectService.find(projectKey);
        if (maybeProject.isEmpty()) {
            return Optional.empty();
        }

        final var maybeTicket = ticketService.find(ticketKey);
        if (maybeTicket.isPresent() && maybeProject.get().getTickets().contains(maybeTicket.get())) {
            return maybeTicket;
        }
        return Optional.empty();
    }
}
