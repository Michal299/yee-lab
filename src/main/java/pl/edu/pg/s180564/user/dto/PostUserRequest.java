package pl.edu.pg.s180564.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pg.s180564.user.entity.User;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostUserRequest implements Serializable {

    private String nickname;
    private String password;
    private String mail;

    public static User mapRequestToObject(final PostUserRequest userRequest) {
        return User.builder()
                .mail(userRequest.mail)
                .nickname(userRequest.nickname)
                .password(userRequest.password)
                .avatar(new byte[0])
                .build();
    }
}
