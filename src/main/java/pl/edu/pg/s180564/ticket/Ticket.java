package pl.edu.pg.s180564.ticket;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.s180564.project.Project;
import pl.edu.pg.s180564.user.entity.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {

    @Id
    private String ticketKey;

    private String summary;

    private String description;

    @OneToOne
    private User creator;

    @OneToOne
    private User assignee;

    private LocalDateTime creationTime;

    @ManyToOne
    private Project project;

    private PriorityType priorityType;
}
