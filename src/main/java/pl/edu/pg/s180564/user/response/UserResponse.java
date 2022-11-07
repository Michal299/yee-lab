package pl.edu.pg.s180564.user.response;

import pl.edu.pg.s180564.user.entity.User;
import pl.edu.pg.s180564.user.entity.UserRoleType;

public class UserResponse {
    private final String nickname;
    private final String mail;
    private final UserRoleType userRole;

    private UserResponse(final String nickname,
                         final String mail,
                         final UserRoleType userRole) {
        this.nickname = nickname;
        this.mail = mail;
        this.userRole = userRole;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMail() {
        return mail;
    }

    public UserRoleType getUserRole() {
        return userRole;
    }

    public static UserResponse mapUserEntityToDto(final User user) {
        return new UserResponse(user.getNickname(),
                user.getMail(),
                user.getUserRole()
        );
    }
}
