package pl.edu.pg.s180564.user.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    private String nickname;

    private String password;

    private String mail;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] avatar;

    private UserRoleType userRole;
}
