package pl.edu.pg.s180564.user;

import java.util.Arrays;
import java.util.Objects;

public class User {
    private final String nickname;
    private final String password;
    private final String mail;
    private final byte[] avatar;
    private final UserRoleType userRole;

    public User(final String nickname,
                final String password,
                final String mail,
                final byte[] avatar,
                final UserRoleType userRole) {
        this.nickname = nickname;
        this.password = password;
        this.mail = mail;
        this.avatar = avatar;
        this.userRole = userRole;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public UserRoleType getUserRole() {
        return userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(nickname, user.nickname) && Objects.equals(password, user.password) && Objects.equals(mail, user.mail) && Arrays.equals(avatar, user.avatar) && userRole == user.userRole;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(nickname, password, mail, userRole);
        result = 31 * result + Arrays.hashCode(avatar);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", avatar=" + Arrays.toString(avatar) +
                ", userRole=" + userRole +
                '}';
    }
}
