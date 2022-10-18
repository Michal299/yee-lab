package pl.edu.pg.s180564.ticket.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pg.s180564.ticket.PriorityType;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PriorityModel {

    private String priority;

    public static PriorityModel mapEntityToModel(PriorityType type) {
        return PriorityModel.builder()
                .priority(type.name())
                .build();
    }

}
