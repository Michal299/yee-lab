package pl.edu.pg.s180564.user.controller;

import pl.edu.pg.s180564.user.dto.PostUserRequest;
import pl.edu.pg.s180564.user.service.UserService;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/users")
public class UserController {

    private UserService userService;

    @EJB
    public void setUserService(final UserService userService) {
        this.userService = userService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(PostUserRequest userRequest) {
        final var user = PostUserRequest.mapRequestToObject(userRequest);
        if (userService.find(user.getNickname()).isPresent()) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        final var createdUser = userService.create(user);
        return Response.created(
            UriBuilder.fromPath("/users")
                .path(createdUser)
                .build()
        ).build();
    }
}
