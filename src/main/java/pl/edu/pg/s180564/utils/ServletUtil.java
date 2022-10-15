package pl.edu.pg.s180564.utils;

import javax.servlet.http.HttpServletRequest;

public final class ServletUtil {

    private ServletUtil() {

    }

    public static String getRequestPath(final HttpServletRequest request) {
        final String path = request.getPathInfo();
        return path == null ? "" : path;
    }

    public enum MimeType {
        APPLICATION_JSON("application/json"),
        IMAGE_PNG("image/png");
        public final String type;
        MimeType(String type) {
            this.type = type;
        }
    }
}
