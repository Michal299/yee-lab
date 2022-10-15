package pl.edu.pg.s180564.user.response;

import pl.edu.pg.s180564.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class AllUsersResponse {
    public static class UserDto {
        private final String nickname;

        private UserDto(final String nickname) {
            this.nickname = nickname;
        }

        public String getNickname() {
            return nickname;
        }
    }

    private final List<UserDto> users;

    private AllUsersResponse(final List<UserDto> users) {
        this.users = users;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public static AllUsersResponse mapUsersToUsersResponse(final List<User> users) {
        return new AllUsersResponse(users.stream()
                .map(user -> new UserDto(user.getNickname()))
                .collect(Collectors.toList())
        );
    }
}
