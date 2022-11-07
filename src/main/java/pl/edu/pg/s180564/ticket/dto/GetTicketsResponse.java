package pl.edu.pg.s180564.ticket.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetTicketsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Ticket {
        private String ticketKey;
        private String ticketSummary;
    }

    private List<Ticket> tickets;

    public static GetTicketsResponse mapToResponse(final List<pl.edu.pg.s180564.ticket.Ticket> tickets) {
        final var ticketsDto = tickets.stream()
            .map(ticket -> Ticket.builder()
                .ticketKey(ticket.getTicketKey())
                .ticketSummary(ticket.getSummary())
                .build())
            .collect(Collectors.toList());
        return new GetTicketsResponse((ticketsDto));
    }
}