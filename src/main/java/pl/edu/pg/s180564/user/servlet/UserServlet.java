package pl.edu.pg.s180564.user.servlet;

import pl.edu.pg.s180564.user.dto.AllUsersResponse;
import pl.edu.pg.s180564.user.dto.PostUserRequest;
import pl.edu.pg.s180564.user.dto.UserResponse;
import pl.edu.pg.s180564.user.service.UserService;
import pl.edu.pg.s180564.utils.ServletUtil;

import javax.ejb.EJB;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = UserServletPaths.USERS_PATH + "/*")
public class UserServlet extends HttpServlet {

    private UserService userService;
    private final Jsonb jsonb = JsonbBuilder.create();

    public UserServlet() {
    }

    @EJB
    public void setUserService(final UserService userService) {
        this.userService = userService;
    }

    public static final class Patterns {
        public static final String USER = "^/[a-zA-Z0-9]*/?";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = ServletUtil.getRequestPath(req);
        final var servletPath = req.getServletPath();
        log("[GET] " + servletPath + path);

        if (path.isEmpty()) {
            getAllUsers(req, resp);
        } else if (path.matches(Patterns.USER)) {
            getUser(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final var path = ServletUtil.getRequestPath(request);
        final var servletPath = request.getServletPath();
        log("[POST] " + servletPath + path);

        if (path.isEmpty()) {
            registerUser(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void getAllUsers(final HttpServletRequest request,
                             final HttpServletResponse response) throws IOException {
        final var users = userService.findAll();
        final var usersResponse = jsonb.toJson(AllUsersResponse.mapUsersToUsersResponse(users));
        response.setContentType(ServletUtil.MimeType.APPLICATION_JSON.type);
        response.getWriter().write(usersResponse);
    }

    private void getUser(final HttpServletRequest request,
                         final HttpServletResponse response) throws IOException {
        final var userId = ServletUtil.getRequestPath(request).replace("/", "");
        final var user = userService.find(userId);
        if (user.isPresent()) {
            response.setContentType(ServletUtil.MimeType.APPLICATION_JSON.type);
            response.getWriter().write(jsonb.toJson(UserResponse.mapUserEntityToDto(user.get())));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void registerUser(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final var userRequest = jsonb.fromJson(request.getReader(), PostUserRequest.class);
        final var user = PostUserRequest.mapRequestToObject(userRequest);
        userService.create(user);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
