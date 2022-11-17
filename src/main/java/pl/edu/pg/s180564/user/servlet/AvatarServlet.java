package pl.edu.pg.s180564.user.servlet;

import pl.edu.pg.s180564.user.entity.User;
import pl.edu.pg.s180564.user.service.UserService;
import pl.edu.pg.s180564.utils.ServletUtil;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

@WebServlet(urlPatterns = AvatarServletPaths.AVATAR_URL + "/*")
@MultipartConfig(maxFileSize = 1024 * 1024)
public class AvatarServlet extends HttpServlet {

    private UserService userService;

    public AvatarServlet() {
    }

    @EJB
    public void setUserService(final UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final var path = ServletUtil.getRequestPath(req);

        if (path.isEmpty() || !path.matches(UserServlet.Patterns.USER)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        doGetAvatarForUser(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final var path = ServletUtil.getRequestPath(req);
        if (path.isEmpty() || !path.matches(UserServlet.Patterns.USER)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        doDeleteAvatarForUser(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = ServletUtil.getRequestPath(req);
        if (path.isEmpty() || !path.matches(UserServlet.Patterns.USER)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        doPostAvatar(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = ServletUtil.getRequestPath(req);
        if (path.isEmpty() || !path.matches(UserServlet.Patterns.USER)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        doPutAvatarForUser(req, resp);
    }

    private void doGetAvatarForUser(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final var user = getUserByNickname(request, response);
        if (user == null) return;

        response.addHeader(HttpHeaders.CONTENT_TYPE, ServletUtil.MimeType.IMAGE_PNG.type);
        response.setContentLength(user.getAvatar().length);
        response.getOutputStream().write(user.getAvatar());
    }

    private void doDeleteAvatarForUser(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final var user = getUserByNickname(request, response);
        if (user == null) return;
        userService.deleteAvatar(user);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void doPutAvatarForUser(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
        final var user = getUserByNickname(request, response);
        if (user == null) return;

        final var avatar = request.getPart("avatar");
        if (avatar != null) {
            userService.updateAvatar(user.getNickname(), avatar.getInputStream());
        }
    }

    private void doPostAvatar(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
        final var user = getUserByNickname(request, response);
        if (user == null) return;

        if (user.getAvatar().length > 0) {
            response.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        }

        final var avatar = request.getPart("avatar");
        if (avatar != null) {
            userService.updateAvatar(user.getNickname(), avatar.getInputStream());
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
    }

    private User getUserByNickname(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final var userNickname = ServletUtil.getRequestPath(request).replace("/", "");
        if (userNickname.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        final var maybeUser = userService.find(userNickname);
        if (maybeUser.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return maybeUser.get();
    }
}
