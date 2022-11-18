package pl.edu.pg.s180564.user.dto;

import pl.edu.pg.s180564.user.entity.User;
import pl.edu.pg.s180564.user.entity.UserRoleType;

import java.util.List;

public class UserResponse {
    private final String nickname;
    private final String mail;
    private final List<String> userRoles;

    private UserResponse(final String nickname,
                         final String mail,
                         final List<String> userRoles) {
        this.nickname = nickname;
        this.mail = mail;
        this.userRoles = userRoles;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMail() {
        return mail;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public static UserResponse mapUserEntityToDto(final User user) {
        return new UserResponse(user.getNickname(),
                user.getMail(),
                user.getUserRoles()
        );
    }
}
